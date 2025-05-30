package com.tech4box.sink;

import com.tech4box.util.AvroUtils;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class AvroFileSink implements DataSink {

    private final File outputDir;
    private final boolean compress;

    public AvroFileSink(File outputDir, boolean compress) {
        this.outputDir = outputDir;
        this.compress = compress;
    }

    @Override
    public void send(String splitKey, int partitionNumber, List<GenericRecord> records, Schema schema) throws IOException {
        File outFile = new File(outputDir, splitKey + "_part" + partitionNumber + ".avro");
        AvroUtils.writeAvro(records, schema, outFile, compress);
    }
}
