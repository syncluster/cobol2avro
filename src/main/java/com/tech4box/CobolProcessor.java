package com.tech4box;

import com.tech4box.sink.DataSink;
import com.tech4box.util.AvroRecordGenerator;
import com.tech4box.util.AvroUtils;
import com.tech4box.JRecord.Details.LayoutDetail;
import com.tech4box.JRecord.IO.AbstractLineReader;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;

import java.io.IOException;
import java.util.*;

public class CobolProcessor {

    public void process(LayoutDetail layout,
                        AbstractLineReader reader,
                        Schema schema,
                        String splitBy,
                        long partitionSizeKb,
                        DataSink sink) throws IOException {

// Buffers for each split key
        Map<String, List<GenericRecord>> bufferMap = new HashMap<>();
        Map<String, Long> sizeMap = new HashMap<>();
        Map<String, Integer> partCounter = new HashMap<>();

        var line = reader.read();

        while (line != null) {
            String key = (splitBy != null) ? Objects.toString(line.getField(splitBy), "default") : "default";

            GenericRecord record = AvroRecordGenerator.fromLine(line, schema, layout);

// Initialize structures if needed
            bufferMap.computeIfAbsent(key, k -> new ArrayList<>());
            sizeMap.putIfAbsent(key, 0L);
            partCounter.putIfAbsent(key, 1);

// Add record to buffer
            bufferMap.get(key).add(record);
            long estimatedSize = AvroUtils.estimateRecordSize(record);
            sizeMap.put(key, sizeMap.get(key) + estimatedSize);

// Check if buffer exceeds partition size
            if (partitionSizeKb > 0 && sizeMap.get(key) >= partitionSizeKb * 1024) {
                int part = partCounter.get(key);
                sink.send(key, part, bufferMap.get(key), schema);

// Reset buffer and update part counter
                bufferMap.put(key, new ArrayList<>());
                sizeMap.put(key, 0L);
                partCounter.put(key, part + 1);
            }

            line = reader.read();
        }
        reader.close();

// Flush remaining buffers
        for (var entry : bufferMap.entrySet()) {
            if (!entry.getValue().isEmpty()) {
                String key = entry.getKey();
                int part = partCounter.get(key);
                sink.send(key, part, entry.getValue(), schema);
            }
        }
    }
}