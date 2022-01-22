# Kafka Demo

## Starting Kafka
./bin/zookeeper-server-start.sh config/zookeeper.properties

./bin/kafka-server-start.sh config/server.properties

## Reading data from Kafka Producer
./kafka-console-consumer.sh --bootstrap-server 127.0.0.1:9092 --topic first_topic --group my-third-application

## Describe topic
./kafka-consumer-groups.sh --bootstrap-server localhost:9092 --group my-fourth-application --describe

## Steps to Demo Consumer Group
1. Run ConsumerDemoGroups 3 times, one by one to create 3 consumers
2. Observe logs how the consumers revoke the assigned partitions and rebalanced
3. Execute ProducerDemo multiple times and observe how data is rebalanced between different consumers
4. Kill on of the consumer, other two consumer will rebalanced the partitions amongst themselves






