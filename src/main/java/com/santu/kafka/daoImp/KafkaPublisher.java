package com.santu.kafka.daoImp;

import com.santu.kafka.dao.Publisher;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.List;
import java.util.Properties;
import java.util.UUID;

public class KafkaPublisher implements Publisher, Runnable {

    private String deviceId;

    private List<String> data;

    private String KafkaTopic = "sample-data";

    KafkaPublisher(String deviceId, List<String> data) {
        this.deviceId = deviceId;
        this.data = data;
    }


    @Override
    public void publish() {
        Properties props = getKafkaProperties();
        // initializing KafkaProducer with properties
        Producer<String, String> producer = new KafkaProducer<String, String>(props);
        System.out.println("Started publishing for DeviceId " + deviceId);
        data.forEach(item -> {
            System.out.println("DeviceId " + deviceId + " Data " + item);
            try {
                //Creating ProducerRecord with data and topicName
                final ProducerRecord<String, String> csvRecord = new ProducerRecord<>(KafkaTopic, UUID.randomUUID().toString(), item);
                // sending message with producer with callback method returning metadata and exception
                producer.send(csvRecord, (metadata, exception) -> {
                    if (metadata != null) {
                        System.out.println("Message sent successfully");
                    } else {
                        System.out.println("Error occured while sending");
                    }

                });
            } catch (Exception e){
                System.out.println("Error in sending record");
                System.out.println(e);
            }
        });
        System.out.println("End of publishing for devideId " + deviceId);
    }

    private Properties getKafkaProperties() {
        Properties props = new Properties();
        //Assign localhost id
        props.put("bootstrap.servers", "localhost:9092");

        //Set acknowledgements for producer requests.
        props.put("acks", "all");

        //If the request fails, the producer can automatically retry,
        props.put("retries", 0);

        //Specify buffer size in config
        props.put("batch.size", 16384);

        //Reduce the no of requests less than 0
        props.put("linger.ms", 1);

        props.put("client.id", "kafka_producer");

        //The buffer.memory controls the total amount of memory available to the producer for buffering.
        props.put("buffer.memory", 33554432);

        props.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");

        props.put("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");

        return props;
    }

    @Override
    public void run() {
        publish();
    }
}
