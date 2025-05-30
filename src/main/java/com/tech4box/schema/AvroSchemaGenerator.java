package com.tech4box.schema;

import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.RecordDetail;
import net.sf.JRecord.Types.TypeManager;
import org.apache.avro.Schema;

import java.util.ArrayList;
import java.util.List;

public class AvroSchemaGenerator {
    private static final TypeManager typeManager = TypeManager.getInstance();
    public static Schema fromLayout(LayoutDetail layout) {
        List<Schema.Field> avroFields = new ArrayList<>();

        int recordCount = layout.getRecordCount();
        for (int recIdx = 0; recIdx < recordCount; recIdx++) {
            RecordDetail record = layout.getRecord(recIdx);
            int fieldCount = record.getFieldCount();

            for (int fldIdx = 0; fldIdx < fieldCount; fldIdx++) {
                FieldDetail field = record.getField(fldIdx);

                Schema.Field avroField = new Schema.Field(
                        sanitizeFieldName(field.getName()),
                        determineAvroType(field),
                        null,
                        (Object) null
                );

                avroFields.add(avroField);
            }
        }

        Schema schema = Schema.createRecord("CobolRecord", null, null, false);
        schema.setFields(avroFields);
        return schema;
    }

    private static Schema determineAvroType(FieldDetail field) {
        int cobolType = field.getType();
        String fieldName = field.getName().toUpperCase();

        boolean isNumeric = typeManager.getType(cobolType).isNumeric();
        boolean isBinary = typeManager.getType(cobolType).isBinary();

// Check if the field has decimal places
        boolean hasDecimal = field.getDecimal() > 0;

        if (isNumeric) {
            if (hasDecimal) {
// Decimal mapping
                return decimalSchema(field);
            } else {
// Integer mapping based on length
                int size = field.getLen();

                if (size <= 9) {
                    return Schema.create(Schema.Type.INT); // fits into 32 bits
                } else {
                    return Schema.create(Schema.Type.LONG); // 64-bit integer
                }
            }
        } else if (isBinary) {
            return Schema.create(Schema.Type.BYTES);
        } else {
            return Schema.create(Schema.Type.STRING);
        }
    }

    private static Schema decimalSchema(FieldDetail field) {
        int precision = field.getDecimal() + (int) Math.ceil(Math.log10(Math.pow(10, field.getLen() - field.getDecimal())));

        Schema decimalSchema = Schema.create(Schema.Type.BYTES);
        decimalSchema.addProp("logicalType", "decimal");
        decimalSchema.addProp("precision", precision);
        decimalSchema.addProp("scale", field.getDecimal());

        return decimalSchema;
    }

    private static String sanitizeFieldName(String name) {
        return name == null ? "UNKNOWN" : name.replace("-", "_").toUpperCase();
    }
}