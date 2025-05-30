package com.tech4box;

import com.tech4box.sink.DataSink;
import com.tech4box.util.AvroRecordGenerator;
import com.tech4box.util.AvroUtils;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.IO.AbstractLineReader;
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

        Map<String, List<GenericRecord>> recordsMap = new HashMap<>();

        var line = reader.read();
        while (line != null) {
            String key = (splitBy != null) ? Objects.toString(line.getField(splitBy), "default") : "default";
            GenericRecord record = AvroRecordGenerator.fromLine(line, schema, layout);

            recordsMap.computeIfAbsent(key, k -> new ArrayList<>()).add(record);
            line = reader.read();
        }
        reader.close();

        for (var entry : recordsMap.entrySet()) {
            String splitKey = entry.getKey();
            List<GenericRecord> records = entry.getValue();

            int part = 1;
            List<GenericRecord> buffer = new ArrayList<>();
            long currentSize = 0;

            for (GenericRecord record : records) {
                buffer.add(record);
                currentSize += AvroUtils.estimateRecordSize(record);

                if (partitionSizeKb > 0 && currentSize >= partitionSizeKb * 1024) {
                    sink.send(splitKey, part, buffer, schema);
                    part++;
                    buffer = new ArrayList<>();
                    currentSize = 0;
                }
            }

            if (!buffer.isEmpty()) {
                sink.send(splitKey, part, buffer, schema);
            }
        }
    }
}
