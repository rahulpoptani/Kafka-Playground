package com.github.rahulpoptani.kafka_avro.v2;

import com.example.v2.Customer;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaAvroProducerV2 {

    public static void main(String[] args) {
        Properties properties = new Properties();
        // normal producer
        properties.setProperty("bootstrap.servers", "127.0.0.1:9092");
        properties.setProperty("acks", "all");
        properties.setProperty("retries", "10");
        // avro part
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", KafkaAvroSerializer.class.getName());
        properties.setProperty("schema.registry.url", "http://127.0.0.1:8081");

        Producer<String, Customer> producer = new KafkaProducer<String, Customer>(properties);

        String topic = "customer-avro1";

        Customer customer = Customer.newBuilder()
                .setAge(34)
                .setFirstName("John")
                .setLastName("Doe")
                .setHeight(178f)
                .setWeight(75f)
                .setEmail("john.doe@example.com")
                .setPhoneNumber("(123)-456-7890")
                .build();

        ProducerRecord<String, Customer> producerRecord = new ProducerRecord<>(topic, customer);

        System.out.println(customer);

        producer.send(producerRecord, new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                if (e == null) {
                    System.out.println(recordMetadata);
                } else {
                    e.printStackTrace();
                }
            }
        });

        producer.flush();
        producer.close();
    }
}
