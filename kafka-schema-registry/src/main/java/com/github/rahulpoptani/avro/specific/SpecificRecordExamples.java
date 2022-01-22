package com.github.rahulpoptani.avro.specific;

import com.example.v1.Customer;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

import java.io.File;
import java.io.IOException;

public class SpecificRecordExamples {

    public static void main(String[] args) {

        // create specific record
        Customer.Builder customerBuilder = Customer.newBuilder();
        customerBuilder.setFirstName("John");
        customerBuilder.setLastName("Doe");
        customerBuilder.setAge(25);
        customerBuilder.setHeight(170f);
        customerBuilder.setWeight(80.5f);
        customerBuilder.setAutomatedEmail(false);
        Customer customer = customerBuilder.build();

        System.out.println(customer);

        // write to file
        final DatumWriter<Customer> datumWriter = new SpecificDatumWriter<>(Customer.class);
        try (DataFileWriter<Customer> dataFileWriter = new DataFileWriter<>(datumWriter)) {
            dataFileWriter.create(customer.getSchema(), new File("customer-specific.avro"));
            dataFileWriter.append(customer);
            System.out.println("successfully wrote customer-specific.avro");
        } catch (IOException e){
            e.printStackTrace();
        }

        // read from file
        final File file = new File("customer-specific.avro");
        final DatumReader<Customer> datumReader = new SpecificDatumReader<>(Customer.class);
        final DataFileReader<Customer> dataFileReader;
        // interpret
        try {
            System.out.println("Reading our specific record");
            dataFileReader = new DataFileReader<>(file, datumReader);
            while (dataFileReader.hasNext()) {
                Customer readCustomer = dataFileReader.next();
                System.out.println(readCustomer.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
