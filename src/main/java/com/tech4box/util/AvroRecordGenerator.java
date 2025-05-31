package com.tech4box.util;

import com.tech4box.JRecord.Common.FieldDetail;
import com.tech4box.JRecord.Details.LayoutDetail;
import com.tech4box.JRecord.Details.RecordDetail;
import com.tech4box.JRecord.Details.AbstractLine;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;

public class AvroRecordGenerator {

    public static GenericRecord fromLine(AbstractLine line, Schema schema, LayoutDetail layout) {
        GenericRecord record = new GenericData.Record(schema);

        RecordDetail recordDetail = layout.getRecord(0);
        int fieldCount = recordDetail.getFieldCount();

        for (int i = 0; i < fieldCount; i++) {
            FieldDetail field = recordDetail.getField(i);
            String fieldName = normalizeFieldName(field.getName());

            Schema.Field avroField = schema.getField(fieldName);
            if (avroField == null) {
                continue; // Skip fields not present in the schema
            }

            Schema fieldSchema = getFieldSchema(avroField.schema());
            Object rawValue = line.getField(field);

            Object avroValue = convertToAvroValue(rawValue, field, fieldSchema);
            record.put(fieldName.replace("-", "_"), avroValue);
        }

        return record;
    }

    private static Object convertToAvroValue(Object rawValue, FieldDetail field, Schema schema) {
        if (rawValue == null) {
            return null;
        }

        switch (schema.getType()) {
            case STRING:
                return rawValue.toString();

            case INT:
                return Integer.parseInt(rawValue.toString().trim());

            case LONG:
                return Long.parseLong(rawValue.toString().trim());

            case BYTES:
                if (isDecimalLogicalType(schema)) {
                    BigDecimal decimal = parseBigDecimal(rawValue, field);
                    return decimalToBytes(decimal, schema);
                } else {
                    return ByteBuffer.wrap(rawValue.toString().getBytes());
                }

            default:
                throw new IllegalArgumentException("Unsupported Avro type: " + schema.getType());
        }
    }

    private static boolean isDecimalLogicalType(Schema schema) {
        return "decimal".equals(schema.getProp("logicalType"));
    }

    private static BigDecimal parseBigDecimal(Object rawValue, FieldDetail field) {
        String valueStr = rawValue.toString().trim().replace(",", ".");

        if (valueStr.isEmpty()) {
            return BigDecimal.ZERO;
        }

        int scale = field.getDecimal();
        return new BigDecimal(new BigInteger(valueStr.replace(".", "")), scale);
    }

    private static ByteBuffer decimalToBytes(BigDecimal decimal, Schema schema) {
        String scaleProp = schema.getProp("scale");
        String precisionProp = schema.getProp("precision");

        int scale = scaleProp != null ? Integer.parseInt(scaleProp) : decimal.scale();
        int precision = precisionProp != null ? Integer.parseInt(precisionProp) : decimal.precision();

        BigDecimal scaledDecimal = decimal.setScale(scale);
        BigInteger unscaled = scaledDecimal.unscaledValue();
        byte[] bytes = unscaled.toByteArray();

        return ByteBuffer.wrap(bytes);
    }

    private static String normalizeFieldName(String fieldName) {
        return fieldName.trim().replace("-", "_");
    }

    private static Schema getFieldSchema(Schema schema) {
        if (schema.getType() == Schema.Type.UNION) {
// Get the non-null type in union
            for (Schema s : schema.getTypes()) {
                if (s.getType() != Schema.Type.NULL) {
                    return s;
                }
            }
        }
        return schema;
    }
}