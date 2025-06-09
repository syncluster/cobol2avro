package com.tech4box;

import com.tech4box.JRecord.Details.AbstractLine;
import com.tech4box.JRecord.Details.LayoutDetail;
import com.tech4box.JRecord.Details.Line;
import com.tech4box.schema.AvroSchemaGenerator;
import com.tech4box.sink.DataSink;
import com.tech4box.util.AvroRecordGenerator;
import com.tech4box.util.AvroUtils;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CobolByteRangeDiscriminatorProcessor {

    private static final Logger log = LoggerFactory.getLogger(CobolByteRangeDiscriminatorProcessor.class);

    private final ExecutorService executorService;
    private final boolean enableParallelFlush;
    private final int podIndex;
    private final int maxRecordsInBuffer;

    public CobolByteRangeDiscriminatorProcessor(int threadCount, boolean enableParallelFlush, int podIndex, int maxRecordsInBuffer) {
        this.executorService = Executors.newFixedThreadPool(threadCount);
        this.enableParallelFlush = enableParallelFlush;
        this.podIndex = podIndex;
        this.maxRecordsInBuffer = maxRecordsInBuffer;
    }

    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                log.warn("Executor did not terminate in time. Forcing shutdown...");
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            log.error("Shutdown interrupted. Forcing shutdown now.");
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    public void processByByteRange(
            File file,
            int discriminatorOffset,
            int discriminatorLength,
            Map<String, AvroSchemaGenerator.SchemaInfo> schemaMap,
            long partitionSizeKb,
            DataSink sink,
            long startByte,
            long endByte
    ) throws IOException {

        AvroSchemaGenerator.SchemaInfo anySchemaInfo = schemaMap.values().iterator().next();
        long recordLength = anySchemaInfo.layout.getMaximumRecordLength();

        long alignedStart = (startByte / recordLength) * recordLength;
        long alignedEnd = (endByte / recordLength) * recordLength;

        RandomAccessFile raf = new RandomAccessFile(file, "r");
        raf.seek(alignedStart);

        Map<String, List<GenericRecord>> bufferMap = new HashMap<>();
        Map<String, Long> sizeMap = new HashMap<>();
        Map<String, Integer> partCounter = new HashMap<>();
        Map<String, Integer> recordCounter = new HashMap<>();

        byte[] recordBuffer = new byte[(int) recordLength];
        long currentPos = alignedStart;
        long totalProcessed = 0;
        long totalSkipped = 0;

        log.info("Started byte-range processing from {} to {}", alignedStart, alignedEnd);

        while (currentPos < alignedEnd) {
            int read = raf.read(recordBuffer);
            if (read < recordLength) {
                log.info("Reached end of range or EOF.");
                break;
            }

            try {
                String data  = new String(recordBuffer);
                String discriminator = new String(recordBuffer, discriminatorOffset, discriminatorLength).trim();
                if (discriminator.isEmpty()) {
                    log.warn("Empty discriminator found at position {}", currentPos);
                    totalSkipped++;
                    currentPos += recordLength;
                    continue;
                }

                AvroSchemaGenerator.SchemaInfo schemaInfo = schemaMap.get(discriminator);

                if (schemaInfo == null) {
                    log.warn("Skipping unknown discriminator: '{}'", discriminator);
                    totalSkipped++;
                    currentPos += recordLength;
                    continue;
                }

                LayoutDetail layout = schemaInfo.layout;
                Schema avroSchema = schemaInfo.schema;
                AbstractLine line = newLine(layout, recordBuffer);

                GenericRecord record = AvroRecordGenerator.fromLine(line, avroSchema, layout);

                bufferMap.computeIfAbsent(discriminator, k -> new ArrayList<>());
                sizeMap.putIfAbsent(discriminator, 0L);
                partCounter.putIfAbsent(discriminator, 1);
                recordCounter.putIfAbsent(discriminator, 0);

                bufferMap.get(discriminator).add(record);
                sizeMap.put(discriminator, sizeMap.get(discriminator) + AvroUtils.estimateRecordSize(record));
                recordCounter.put(discriminator, recordCounter.get(discriminator) + 1);

                if ((partitionSizeKb > 0 && sizeMap.get(discriminator) >= partitionSizeKb * 1024)
                        || recordCounter.get(discriminator) >= maxRecordsInBuffer) {

                    flushBufferAsync(sink, discriminator, partCounter.get(discriminator), bufferMap.get(discriminator), avroSchema);
                    partCounter.put(discriminator, partCounter.get(discriminator) + 1);
                    bufferMap.put(discriminator, new ArrayList<>());
                    sizeMap.put(discriminator, 0L);
                    recordCounter.put(discriminator, 0);
                }

                totalProcessed++;
                currentPos += recordLength;

                if (totalProcessed % 10000 == 0) {
                    log.info("Processed {} records...", totalProcessed);
                }

            } catch (Exception e) {
                log.error("Failed to process record at byte position {}. Skipping. Error: {}", currentPos, e.getMessage(), e);
                totalSkipped++;
                currentPos += recordLength;
            }
        }

        raf.close();

        for (var entry : bufferMap.entrySet()) {
            if (!entry.getValue().isEmpty()) {
                String key = entry.getKey();
                int part = partCounter.get(key);
                flushBufferAsync(sink, key, part, entry.getValue(), schemaMap.get(key).schema);
            }
        }

        shutdown();
        log.info("Finished processing. Records processed: {}, Skipped: {}", totalProcessed, totalSkipped);
    }

    private void flushBufferAsync(DataSink sink, String key, int partNumber, List<GenericRecord> records, Schema schema) {
        Runnable task = () -> {
            try {
                String filenameSuffix = String.format("%s_part%d_pod%d", key, partNumber, podIndex);
                log.info("Flushing file: {}", filenameSuffix);
                sink.send(filenameSuffix, partNumber, records, schema);
            } catch (Exception e) {
                log.error("Failed to flush buffer for {} part {}: {}", key, partNumber, e.getMessage(), e);
                throw new RuntimeException(e);
            }
        };

        if (enableParallelFlush) {
            executorService.submit(task);
        } else {
            task.run();
        }
    }

    private AbstractLine newLine(LayoutDetail layout, byte[] data) {
        AbstractLine line = new Line(layout);
        log.debug("Layout record length: {}", layout.getMaximumRecordLength());
        log.debug("Raw bytes: [{}]", new String(data));
        line.setData(data);
        return line;
    }
}