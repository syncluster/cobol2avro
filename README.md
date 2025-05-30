# cobol2avro

A Java library to convert COBOL Copybook files and their data into Avro format. This tool simplifies the modernization of legacy COBOL data for integration into modern data pipelines.

---

## ✨ Features

- ✅ Convert COBOL Copybook into Avro schema.
- ✅ Parse COBOL data files into Avro binary.
- ✅ Kafka ready — stream COBOL data to Kafka topics.
- ✅ Supports multiple COBOL file structures (Fixed, VB, etc.).
- ✅ Easy integration with Spring Boot or plain Java.
- ✅ Save Avro schema to file for auditing or validation.

---

## 📦 Installation

### Maven

```xml
<dependency>
<groupId>com.tech4box</groupId>
<artifactId>cobol2avro</artifactId>
<version>1.0.0</version>
</dependency>
```

### Gradle

```groovy
implementation 'com.tech4box:cobol2avro:1.0.0'
```

---

## 🚀 Basic Usage Example

### ✅ Convert COBOL Copybook + Data File to Avro

```java
import com.tech4box.cobol2avro.CobolAvroConverter;
import java.io.File;

public class Example {
public static void main(String[] args) throws Exception {
CobolAvroConverter converter = new CobolAvroConverter();

converter.convert(
new File("src/main/resources/mycopybook.cbl"),
new File("src/main/resources/mydatafile.dat"),
new File("output/output.avro")
);
}
}
```

### ✅ Generate Avro Schema from Copybook Only

```java
import com.tech4box.cobol2avro.AvroUtils;
import java.io.File;

public class SchemaExample {
public static void main(String[] args) throws Exception {
var schema = AvroUtils.convertCopybookToAvroSchema(
new File("src/main/resources/mycopybook.cbl")
);
System.out.println(schema.toString(true)); // Pretty print schema
}
}
```

### ✅ Stream COBOL Data to Kafka

```java
import com.tech4box.cobol2avro.kafka.KafkaCobolService;
import org.springframework.beans.factory.annotation.Autowired;

@Autowired
KafkaCobolService kafkaCobolService;

kafkaCobolService.processFile(
"src/main/resources/mycopybook.cbl",
"src/main/resources/mydatafile.dat"
);
```

---

## 🛠️ Build from Source

```bash
git clone https://github.com/tech4box/cobol2avro.git
cd cobol2avro
mvn clean install
```

---

## ⚙️ Requirements

- Java 11 or higher
- Maven 3.8+
- Kafka (optional for Kafka features)

---

## 📄 License

This project uses the **BSD License**, aligned with the licenses of:

- **JRecords:** https://sourceforge.net/projects/jrecords/
- **CB2XML:** https://cb2xml.sourceforge.net/

This library leverages JRecords and CB2XML. We comply with their licenses and give full credit to their authors.

---

## 👥 Developers

| Name | Email |
|-----------|----------------------------|
| Eduardo | eduardo@syncluster.com |

---

## 🔗 Related Projects

- [JRecords](https://sourceforge.net/projects/jrecords/)
- [CB2XML](https://cb2xml.sourceforge.net/)

---

## 🤝 Contributing

Contributions are welcome! Feel free to open issues or submit pull requests.

---

## 💡 Roadmap

- ✔️ Kafka sink support
- ✔️ Spring Boot integration
- 🔲 Spark or Apache Beam integration (planned)
- 🔲 JSON output support (planned)
- 🔲 Dockerized version (planned)

---

## 📫 Contact

📧 **eduardo@syncluster.com**

---