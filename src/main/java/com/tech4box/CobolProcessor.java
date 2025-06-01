package com.tech4box;

import com.tech4box.sink.DataSink;
import com.tech4box.util.AvroRecordGenerator;
import com.tech4box.util.AvroUtils;
import com.tech4box.JRecord.Details.LayoutDetail;
import com.tech4box.JRecord.IO.AbstractLineReader;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

public class CobolProcessor {

    private static final Logger log = LoggerFactory.getLogger(CobolProcessor.class);

    private final ExecutorService executorService;
    private final boolean enableParallelFlush;

    public CobolProcessor(int threadCount, boolean enableParallelFlush) {
        this.executorService = Executors.newFixedThreadPool(threadCount);
        this.enableParallelFlush = enableParallelFlush;
    }

    public void shutdown() {
        executorService.shutdown();
    }

    public void process(
            LayoutDetail layout,
            AbstractLineReader reader,
            Schema schema,
            String splitBy,
            long partitionSizeKb,
            DataSink sink
    ) throws IOException {

        Map<String, List<GenericRecord>> bufferMap = new HashMap<>();
        Map<String, Long> sizeMap = new HashMap<>();
        Map<String, Integer> partCounter = new HashMap<>();

        var line = reader.read();
        long recordCount = 0;

        log.info("Started processing...");

        while (line != null) {
            String key = (splitBy != null) ? Objects.toString(line.getField(splitBy), "default") : "default";

            GenericRecord record = AvroRecordGenerator.fromLine(line, schema, layout);

            bufferMap.computeIfAbsent(key, k -> new ArrayList<>());
            sizeMap.putIfAbsent(key, 0L);
            partCounter.putIfAbsent(key, 1);

            bufferMap.get(key).add(record);
            long estimatedSize = AvroUtils.estimateRecordSize(record);
            sizeMap.put(key, sizeMap.get(key) + estimatedSize);

// Flush if buffer exceeds partition size
            if (partitionSizeKb > 0 && sizeMap.get(key) >= partitionSizeKb * 1024) {
                flushBufferAsync(sink, key, partCounter.get(key), bufferMap.get(key), schema);
                partCounter.put(key, partCounter.get(key) + 1);
                bufferMap.put(key, new ArrayList<>());
                sizeMap.put(key, 0L);
            }

            recordCount++;
            if (recordCount % 10000 == 0) {
                log.info("Processed {} records so far...", recordCount);
            }

            line = reader.read();
        }
        reader.close();

// Flush remaining buffers
        for (var entry : bufferMap.entrySet()) {
            if (!entry.getValue().isEmpty()) {
                String key = entry.getKey();
                int part = partCounter.get(key);
                flushBufferAsync(sink, key, part, entry.getValue(), schema);
            }
        }

        log.info("Finished processing. Total records processed: {}", recordCount);

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
                log.info("Flushing partition {}_part{}", key, partNumber);
                sink.send(key, partNumber, records, schema);
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
}