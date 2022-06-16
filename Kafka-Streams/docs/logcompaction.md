# Log Compaction
#### [New Terminal] Start Kafka Cluster using Docker
docker run --rm -it -p 2181:2181 -p 3030:3030 -p 8081:8081 -p 8082:8082 -p 8083:8083 -p 9092:9092 -e ADV_HOST=127.0.0.1 landoop/fast-data-dev

#### [New Terminal] Run bash inside docker to view log compaction and segment create/deletes
docker exec -it <ID> tail -f /var/log/broker.log

#### [New Terminal] Run bash inside docker to create topics
docker run --rm -it --net=host landoop/fast-data-dev bash

kafka-topics --zookeeper 127.0.0.1:2181 --create --topic employee-salary-compact --partitions 1 --replication-factor 1 --config cleanup.policy=compact --config min.cleanable.dirty.ratio=0.005 --config segment.ms=10000

#### Start Consumer
kafka-console-consumer --bootstrap-server 127.0.0.1:9092 --topic employee-salary-compact --from-beginning --property print.key=true --property key.separator=,

#### Start Producer
kafka-console-producer --broker-list 127.0.0.1:9092 --topic employee-salary-compact --property parse.key=true --property key.separator=,

##### Producer Messages
123,{"John":"80000"}
456,{"Mark":"90000"}
789,{"Lisa":"95000"}
##### Updated Messages. Send after sometime and observe the broker logs, restart consumer from beginning, and the changes would be reflected
111,{"A":"10"}
123,{"John":"100000"}
789,{"Lisa":"110000"}