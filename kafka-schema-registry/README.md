## Start Kafka

Docker Compose File: src/main/resources/docker/docker-compose.yml
Syntax: docker-compose up

## Start Confluent Services (if want to test with console producer/consumer)

docker run -it --rm --net=host confluentinc/cp-schema-registry:3.3.1 bash

For Demo, separate out both Kafka Producer Consumer versions to separate java modules so that they have individual resources and generated-classes with same schema name
same structure but different schema inside resources/avro/customer.avsc file

Note: Ideal way, is to generate the SpecificRecord and reference the Jar in your code.

 

