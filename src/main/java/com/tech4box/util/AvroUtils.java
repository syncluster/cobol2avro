package com.tech4box.util;

import org.apache.avro.Schema;
import org.apache.avro.file.CodecFactory;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class AvroUtils {

    public static void writeAvro(List<GenericRecord> records, Schema schema, File outputFile, boolean compress) throws IOException {
        var datumWriter = new GenericDatumWriter<GenericRecord>(schema);
        var dataFileWriter = new DataFileWriter<>(datumWriter);

        if (compress) {
            dataFileWriter.setCodec(CodecFactory.snappyCodec());
        }

        dataFileWriter.create(schema, outputFile);
        for (GenericRecord record : records) {
            dataFileWriter.append(record);
        }
        dataFileWriter.close();
    }

    public static byte[] serializeToAvroBytes(List<GenericRecord> records, Schema schema, boolean compress) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        var datumWriter = new GenericDatumWriter<GenericRecord>(schema);
        var dataFileWriter = new DataFileWriter<>(datumWriter);

        if (compress) {
            dataFileWriter.setCodec(CodecFactory.snappyCodec());
        }

        dataFileWriter.create(schema, out);
        for (GenericRecord record : records) {
            dataFileWriter.append(record);
        }
        dataFileWriter.close();
        return out.toByteArray();
    }

    public static long estimateRecordSize(GenericRecord record) {
        return record.toString().getBytes().length;
    }

    /**
     * Save the Avro schema to a file in the user's home directory under /avro-schemas/
     */
    public static void saveSchemaToFile(Schema schema, String topic, String splitKey, int partition) {
        try {
            String userHome = System.getProperty("user.home");
            File dir = new File(userHome, "avro-schemas");
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String fileName = String.format("%s-schema-%s-part%d.avsc", topic, splitKey, partition);
            File schemaFile = new File(dir, fileName);

            try (FileOutputStream fos = new FileOutputStream(schemaFile)) {
                String prettySchema = schema.toString(true);
                fos.write(prettySchema.getBytes(StandardCharsets.UTF_8));
                fos.flush();
            }
        } catch (Exception e) {
            System.err.println("Warning: Failed to write schema file. " + e.getMessage());
// Fail silently for schema IO errors (does not affect main logic)
        }
    }
}
