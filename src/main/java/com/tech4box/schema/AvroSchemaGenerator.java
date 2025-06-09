package com.tech4box.schema;

import com.tech4box.JRecord.Common.Constants;
import com.tech4box.JRecord.Common.FieldDetail;
import com.tech4box.JRecord.Details.LayoutDetail;
import com.tech4box.JRecord.Details.RecordDetail;
import com.tech4box.JRecord.External.CopybookLoaderFactory;
import com.tech4box.JRecord.External.ExternalRecord;
import com.tech4box.JRecord.IO.CobolIoProvider;
import com.tech4box.JRecord.Types.TypeManager;
import com.tech4box.cb2xml.analysis.Copybook;
import org.apache.avro.Schema;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private static final Pattern LEVEL_PATTERN = Pattern.compile("^\\s*(\\d+)\\s+");

    public static void split(File inputCbl, List<String> discrs, File outputDir) throws IOException, IOException {
        if (!outputDir.exists()) outputDir.mkdirs();

        List<String> header = new ArrayList<>();
        List<List<String>> redefineBlocks = new ArrayList<>();

        List<String> current = null;
        int currentLevel = -1;

        for (String raw : Files.readAllLines(inputCbl.toPath())) {
            String line = raw;
            Matcher m = LEVEL_PATTERN.matcher(line);
            if (!m.find()) continue;

            int level = Integer.parseInt(m.group(1));
            if (line.toUpperCase().contains("REDEFINES") && level == currentLevel) {
// start a new redefine at same level
                current = new ArrayList<>();
                redefineBlocks.add(current);
                current.add(line);
            } else if (line.toUpperCase().contains("REDEFINES")) {
                currentLevel = level;
                current = new ArrayList<>();
                redefineBlocks.add(current);
                current.add(line);
            } else if (current != null && level > currentLevel) {
                current.add(line);
            } else if (current == null) {
                header.add(line);
            }
        }

        if (discrs.size() != redefineBlocks.size()) {
            throw new IllegalArgumentException(
                    "Need " + redefineBlocks.size() + " discriminators, but got " + discrs.size());
        }

        for (int i = 0; i < redefineBlocks.size(); i++) {
            String disc = discrs.get(i);
            File out = new File(outputDir, disc + ".cbl");
            try (BufferedWriter w = new BufferedWriter(new FileWriter(out))) {
                for (String h : header) w.write(h + "\n");
                for (String blk : redefineBlocks.get(i)) w.write(blk + "\n");
            }
            System.out.println("Generated: " + out);
        }
    }

    public static class SchemaInfo {
        public final LayoutDetail layout;
        public final Schema schema;

        public SchemaInfo(LayoutDetail layout, Schema schema) {
            this.layout = layout;
            this.schema = schema;
        }
    }

    public static Map<String, SchemaInfo> loadSchemasFromCopybooks(File copybookDir, String encoding) throws Exception {
        Map<String, SchemaInfo> schemaMap = new HashMap<>();

        File[] files = copybookDir.listFiles((dir, name) -> name.endsWith(".cbl"));
        if (files == null) throw new IllegalArgumentException("No .cbl files found in " + copybookDir);

        for (File file : files) {
            String discriminator = file.getName().substring(0, file.getName().lastIndexOf(".")); // e.g., "employee1.cbl" -> "1"
            //encoding "cp037" or "UTF-8" cp1252
            LayoutDetail layout = CobolSchemaLoader.loadLayoutFromCobol(file.getAbsolutePath(), encoding);
            Schema schema = AvroSchemaGenerator.fromLayout(layout); // your custom generator
            schemaMap.put(discriminator, new SchemaInfo(layout, schema));
        }

        return schemaMap;
    }
}