#### open a shell - zookeeper is at localhost:2181
bin/zookeeper-server-start.sh config/zookeeper.properties

#### open another shell - kafka is at localhost:9092
bin/kafka-server-start.sh config/server.properties

#### create input topic with one partition to get full ordering
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic favourite-colour-input

#### create intermediary log compacted topic
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic user-keys-and-colours --config cleanup.policy=compact

#### create output log compacted topic
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic favourite-colour-output --config cleanup.policy=compact

#### launch the streams application

#### launch a Kafka consumer
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 \
    --topic favourite-colour-output \
    --from-beginning \
    --formatter kafka.tools.DefaultMessageFormatter \
    --property print.key=true \
    --property print.value=true \
    --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer \
    --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer

#### launch kafka producer and produce data
bin/kafka-console-producer.sh --broker-list localhost:9092 --topic favourite-colour-input

rahul,blue
john,green
rahul,red
alice,red

#### list all topics that we have in Kafka (so we can observe the internal topics)
bin/kafka-topics.sh --list --zookeeper localhost:2181
