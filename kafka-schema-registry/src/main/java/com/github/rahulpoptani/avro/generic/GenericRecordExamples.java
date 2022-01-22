package com.github.rahulpoptani.avro.generic;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.*;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;

import java.io.File;
import java.io.IOException;

public class GenericRecordExamples {

    public static void main(String[] args) {

        // Define Schema
        Schema.Parser parser = new Schema.Parser();
        Schema schema = parser.parse("{\n" +
                "     \"type\": \"record\",\n" +
                "     \"namespace\": \"com.example\",\n" +
                "     \"name\": \"Customer\",\n" +
                "     \"doc\": \"Avro Schema for our Customer\",     \n" +
                "     \"fields\": [\n" +
                "       { \"name\": \"first_name\", \"type\": \"string\", \"doc\": \"First Name of Customer\" },\n" +
                "       { \"name\": \"last_name\", \"type\": \"string\", \"doc\": \"Last Name of Customer\" },\n" +
                "       { \"name\": \"age\", \"type\": \"int\", \"doc\": \"Age at the time of registration\" },\n" +
                "       { \"name\": \"height\", \"type\": \"float\", \"doc\": \"Height at the time of registration in cm\" },\n" +
                "       { \"name\": \"weight\", \"type\": \"float\", \"doc\": \"Weight at the time of registration in kg\" },\n" +
                "       { \"name\": \"automated_email\", \"type\": \"boolean\", \"default\": true, \"doc\": \"Field indicating if the user is enrolled in marketing emails\" }\n" +
                "     ]\n" +
                "}");

        // Create Generic Record
        GenericRecordBuilder customerBuilder1 = new GenericRecordBuilder(schema);
        customerBuilder1.set("first_name", "John");
        customerBuilder1.set("last_name", "Doe");
        customerBuilder1.set("age", 25);
        customerBuilder1.set("height", 170f);
        customerBuilder1.set("weight", 80.5f);
        customerBuilder1.set("automated_email", false);
        GenericData.Record customer1 = customerBuilder1.build();

        GenericRecordBuilder customerBuilder2 = new GenericRecordBuilder(schema);
        customerBuilder2.set("first_name", "Mike");
        customerBuilder2.set("last_name", "Taylor");
        customerBuilder2.set("age", 39);
        customerBuilder2.set("height", 180f);
        customerBuilder2.set("weight", 92.5f);
        GenericData.Record customer2 = customerBuilder2.build();

        System.out.println(customer1);
        System.out.println(customer2);

        // Write Generic Record to file
        final DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<>(schema);
        try(DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<>(datumWriter)) {
            dataFileWriter.create(schema, new File("customer-generic.avro")); // we can also fetch schema from customer1.getSchema()
            dataFileWriter.append(customer1);
            dataFileWriter.append(customer2);
            System.out.println("Written customer-generic.avro");
        } catch (IOException e) {
            System.out.println("Couldn't write file");
            e.printStackTrace();
        }
        // Read Generic Record from file
        final File file = new File("customer-generic.avro");
        final DatumReader<GenericRecord> datumReader = new GenericDatumReader<>();
        GenericRecord customerRead;
        // Interpret as Generic Record
        try(DataFileReader<GenericRecord> dataFileReader = new DataFileReader<>(file, datumReader)) {
            while (dataFileReader.hasNext()) {
                customerRead = dataFileReader.next();
                System.out.println(customerRead.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
