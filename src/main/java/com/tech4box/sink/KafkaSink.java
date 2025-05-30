package com.tech4box.sink;

import com.tech4box.util.AvroUtils;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;
import java.util.List;

public class KafkaSink implements DataSink {

    private final KafkaProducer<String, byte[]> producer;
    private final String topic;
    private final String correlationId;
    private final boolean compress;

    public KafkaSink(KafkaProducer<String, byte[]> producer, String topic, String correlationId, boolean compress) {
        this.producer = producer;
        this.topic = topic;
        this.correlationId = correlationId;
        this.compress = compress;
    }

    @Override
    public void send(String splitKey, int partitionNumber, List<GenericRecord> records, Schema schema) throws IOException {
        byte[] payload = AvroUtils.serializeToAvroBytes(records, schema, compress);

        ProducerRecord<String, byte[]> record = new ProducerRecord<>(topic, splitKey + "_part" + partitionNumber, payload);
        record.headers().add("correlationId", correlationId.getBytes());
        record.headers().add("partitionNumber", Integer.toString(partitionNumber).getBytes());
        record.headers().add("splitKey", splitKey.getBytes());

        producer.send(record);
    }
}
