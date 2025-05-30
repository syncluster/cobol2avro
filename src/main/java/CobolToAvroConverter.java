import com.tech4box.schema.AvroSchemaGenerator;
import com.tech4box.util.AvroRecordGenerator;
import com.tech4box.util.AvroUtils;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.LineIOProvider;
import net.sf.JRecord.IO.CobolIoProvider;
import net.sf.JRecord.def.IO.builders.ICobolIOBuilder;
import org.apache.avro.Schema;
import org.apache.avro.file.CodecFactory;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class CobolToAvroConverter {

    public static void main(String[] args) throws Exception {
        if (args.length < 3) {
            System.out.println("Usage: java -jar cobol2avro.jar <copybook.cbl> <datafile.dat> <output-dir> " +
                    "[--split-by=FIELD] [--compress=snappy] [--partition-size=SIZE_KB]");
            return;
        }

        String copybookPath = args[0];
        String dataFilePath = args[1];
        String outputDir = args[2];

        String splitBy = null;
        boolean compress = false;
        long partitionSizeKb = 0;

//        String copybookPath = "src/main/resources/sample.cbl";
//        String dataFilePath = "src/main/resources/sample.dat";
//        String outputDir = "target/avro-output";
//
//        String splitBy = "";
//        boolean compress = true;
//        long partitionSizeKb = 0;

        for (String arg : args) {
            if (arg.startsWith("--split-by=")) {
                splitBy = arg.substring("--split-by=".length()).trim();
            } else if (arg.equals("--compress=snappy")) {
                compress = true;
            } else if (arg.startsWith("--partition-size=")) {
                partitionSizeKb = Long.parseLong(arg.substring("--partition-size=".length()).trim());
            }
        }

        new File(outputDir).mkdirs();

        System.out.println("Loading copybook...");
        ICobolIOBuilder ioBuilder = CobolIoProvider.getInstance().newIOBuilder(copybookPath);
        LayoutDetail layout = ioBuilder.getLayout();

        Schema avroSchema = AvroSchemaGenerator.fromLayout(layout);
        System.out.println("Generated Avro schema:\n" + avroSchema.toString(true));
        AvroUtils.saveSchemaToFile(avroSchema, outputDir, "default", 1);
        Map<String, List<GenericRecord>> recordsMap = new ConcurrentHashMap<>();
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        System.out.println("Reading data...");
        AbstractLineReader reader = LineIOProvider.getInstance().getLineReader(layout.getFileStructure());
        reader.open(new FileInputStream(dataFilePath), layout);

        var line = reader.read();
        while (line != null) {
            GenericRecord record = AvroRecordGenerator.fromLine(line, avroSchema, layout);

            String key;
            if (splitBy != null) {
                Object splitValue = line.getField(splitBy);
                if (splitValue == null || splitValue.toString().trim().isEmpty()) {
// Skip this line if split field is missing or empty
                    line = reader.read();
                    continue;
                }
                key = splitValue.toString().trim();
            } else {
                key = "default";
            }

            recordsMap.computeIfAbsent(key, k -> Collections.synchronizedList(new ArrayList<>())).add(record);
            line = reader.read();
        }
        reader.close();

        System.out.println("Writing Avro files...");
        long finalPartitionSizeKb = partitionSizeKb;
        boolean finalCompress = compress;
        List<Callable<Void>> tasks = recordsMap.entrySet().stream().map(entry -> (Callable<Void>) () -> {
            String key = entry.getKey();
            List<GenericRecord> records = entry.getValue();

            if (finalPartitionSizeKb > 0) {
                writeWithPartition(records, avroSchema, outputDir, key, finalCompress, finalPartitionSizeKb);
            } else {
                writeAvro(records, avroSchema, new File(outputDir, key + ".avro"), finalCompress);
            }

            return null;
        }).collect(Collectors.toList());

        executor.invokeAll(tasks);
        executor.shutdown();

        System.out.println("Process completed.");

        readAvro(outputDir);

    }

    private static void writeWithPartition(List<GenericRecord> records, Schema schema, String outputDir,
                                           String key, boolean compress, long partitionSizeKb) throws IOException {
        int part = 1;
        List<GenericRecord> buffer = new ArrayList<>();

        long currentSize = 0;
        for (GenericRecord record : records) {
            buffer.add(record);
            currentSize += estimateRecordSize(record);

            if (currentSize >= partitionSizeKb * 1024) {
                File outFile = new File(outputDir, key + "_part" + part + ".avro");
                writeAvro(buffer, schema, outFile, compress);
                part++;
                buffer.clear();
                currentSize = 0;
            }
        }

        if (!buffer.isEmpty()) {
            File outFile = new File(outputDir, key + "_part" + part + ".avro");
            writeAvro(buffer, schema, outFile, compress);
        }
    }

    private static long estimateRecordSize(GenericRecord record) {
        return record.toString().getBytes().length;
    }

    private static void writeAvro(List<GenericRecord> records, Schema schema, File outputFile, boolean compress) throws IOException {
        DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<>(schema);
        DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<>(datumWriter);

        if (compress) {
            dataFileWriter.setCodec(CodecFactory.snappyCodec());
        }

        dataFileWriter.create(schema, outputFile);
        for (GenericRecord record : records) {
            dataFileWriter.append(record);
        }
        dataFileWriter.close();
    }
    private static void readAvro(String output) throws IOException {
        File folder = new File(output);
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".avro"));

        if (files == null || files.length == 0) {
            System.out.println("No Avro files found.");
            return;
        }

        for (File file : files) {
            System.out.println("Reading file: " + file.getName());

            DatumReader<GenericRecord> datumReader = new GenericDatumReader<>();
            DataFileReader<GenericRecord> dataFileReader = new DataFileReader<>(file, datumReader);

            while (dataFileReader.hasNext()) {
                GenericRecord record = dataFileReader.next();
                System.out.println(record);
            }

            dataFileReader.close();
        }
    }
}