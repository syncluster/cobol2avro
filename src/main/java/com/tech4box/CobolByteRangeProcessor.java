package com.tech4box;

import com.tech4box.JRecord.Details.AbstractLine;
import com.tech4box.JRecord.Details.LayoutDetail;
import com.tech4box.JRecord.Details.Line;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CobolByteRangeProcessor {

    private static final Logger log = LoggerFactory.getLogger(CobolByteRangeProcessor.class);

    private final ExecutorService executorService;
    private final boolean enableParallelFlush;
    private final int podIndex; // <- Added pod index

    public CobolByteRangeProcessor(int threadCount, boolean enableParallelFlush, int podIndex) {
        this.executorService = Executors.newFixedThreadPool(threadCount);
        this.enableParallelFlush = enableParallelFlush;
        this.podIndex = podIndex;
    }

    public void shutdown() {
        executorService.shutdown();
    }

    public void processByByteRange(
            LayoutDetail layout,
            File file,
            Schema schema,
            String splitBy,
            long partitionSizeKb,
            DataSink sink,
            long startByte,
            long endByte
    ) throws IOException {

        RandomAccessFile raf = new RandomAccessFile(file, "r");
        long recordLength = layout.getMaximumRecordLength();

        long alignedStart = (startByte / recordLength) * recordLength;
        long alignedEnd = (endByte / recordLength) * recordLength;

        raf.seek(alignedStart);

        Map<String, List<GenericRecord>> bufferMap = new HashMap<>();
        Map<String, Long> sizeMap = new HashMap<>();
        Map<String, Integer> partCounter = new HashMap<>();

        long currentPos = alignedStart;
        long recordCount = 0;

        byte[] recordBuffer = new byte[(int) recordLength];

        log.info("Started byte-range processing from {} to {}", alignedStart, alignedEnd);

        while (currentPos < alignedEnd) {
            int read = raf.read(recordBuffer);
            if (read < recordLength) {
                log.info("Reached end of range or EOF.");
                break;
            }

            AbstractLine line = newLine(layout, recordBuffer);
            String key = resolveSplitKey(layout, line, splitBy);

            GenericRecord record = AvroRecordGenerator.fromLine(line, schema, layout);

            bufferMap.computeIfAbsent(key, k -> new ArrayList<>());
            sizeMap.putIfAbsent(key, 0L);
            partCounter.putIfAbsent(key, 1);

            bufferMap.get(key).add(record);
            long estimatedSize = AvroUtils.estimateRecordSize(record);
            sizeMap.put(key, sizeMap.get(key) + estimatedSize);

            if (partitionSizeKb > 0 && sizeMap.get(key) >= partitionSizeKb * 1024) {
                flushBufferAsync(sink, key, partCounter.get(key), bufferMap.get(key), schema);
                partCounter.put(key, partCounter.get(key) + 1);
                bufferMap.put(key, new ArrayList<>());
                sizeMap.put(key, 0L);
            }

            currentPos += recordLength;
            recordCount++;

            if (recordCount % 10000 == 0) {
                log.info("Processed {} records in range...", recordCount);
            }
        }

        raf.close();

// Flush remaining buffers
        for (var entry : bufferMap.entrySet()) {
            if (!entry.getValue().isEmpty()) {
                String key = entry.getKey();
                int part = partCounter.get(key);
                flushBufferAsync(sink, key, part, entry.getValue(), schema);
            }
        }

        log.info("Finished byte-range processing. Total records processed: {}", recordCount);
        shutdown();
    }

    private void flushBufferAsync(
            DataSink sink,
            String key,
            int partNumber,
            List<GenericRecord> records,
            Schema schema
    ) {
        Runnable task = () -> {
            try {
                String filenameSuffix = String.format("%s_part%d_pod%d", key, partNumber, podIndex);
                log.info("Flushing file: {}", filenameSuffix);
                sink.send(filenameSuffix, partNumber, records, schema);
            } catch (Exception e) {
                log.error("Failed to flush partition {}_part{}", key, partNumber, e);
                throw new RuntimeException(e);
            }
        };

        if (enableParallelFlush) {
            executorService.submit(task);
        } else {
            task.run();
        }
    }

    private String resolveSplitKey(LayoutDetail layout, AbstractLine line, String splitBy) {
        if (splitBy == null || splitBy.isBlank()) {
            return "default";
        }
        Object field = line.getFieldValue(splitBy);
        return (field != null) ? field.toString() : "default";
    }

    private AbstractLine newLine(LayoutDetail layout, byte[] data) {
        AbstractLine line = new Line(layout);
        line.replace(data, 0, data.length);

        return line;
    }
}
