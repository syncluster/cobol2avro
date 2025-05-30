package com.tech4box.sink;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import java.io.IOException;
import java.util.List;

public interface DataSink {
    void send(String splitKey, int partitionNumber, List<GenericRecord> records, Schema schema) throws IOException;
}
