package com.github.rahulpoptani.kafka.beginner;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerDemoWithCallback {

    public static void main(String[] args) {

        final Logger logger = LoggerFactory.getLogger(ProducerDemoWithCallback.class);

        // Create Producer Properties
        String bootstrapServers = "127.0.0.1:9092";
        Properties producerProperties = new Properties();
        producerProperties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        producerProperties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProperties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // Create Producer
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(producerProperties);

        // Create Producer Record
        ProducerRecord<String, String> record = new ProducerRecord<String, String>("first_topic", "Hello World");

        // Send Data - asynchronous
        producer.send(record, new Callback() {
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                // executes everytime a record is successfully sent or a exception is thrown
                if (e == null) {
                    // record was successfully sent
                    logger.info("Received new metadata. \n" +
                            "Topic: {} \n" +
                            "Partition: {} \n" +
                            "Offset: {} \n" +
                            "Timestamp: {} \n",
                            recordMetadata.topic(), recordMetadata.partition(),
                            recordMetadata.offset(), recordMetadata.timestamp());
                } else {
                    logger.error("Error while producing", e);
                }
            }
        });

        // flush and close producer
        producer.close();

    }
}
