package org.example.transfersv6;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

import static org.example.transfersv6.Utils.TRANSFERS_TOPIC;

public class MyKafkaProducer {

    private final KafkaProducer<String, String> producer = new KafkaProducer<>(createProperties());


    public static void main(String[] args) {
        new MyKafkaProducer().submitTransfers(80_000_000, 640);
    }

    public void submitTransfers(int numTransfersTotal, int numberOfSenders) {
        ThreadLocalRandom current = ThreadLocalRandom.current();
        for (int j = 0; j < numTransfersTotal; j++) {
            int from = current.nextInt(0, numberOfSenders);
            int to = getRandomId(from, current, numberOfSenders);
            ProducerRecord<String, String> record = new ProducerRecord<>(TRANSFERS_TOPIC, "A" + from + "|A" + to + "|" + current.nextInt(1, 5));
            producer.send(record);
        }
    }

    private int getRandomId(int id, ThreadLocalRandom current, int numberOfSenders) {
        while (true) {
            int nextId = current.nextInt(0, numberOfSenders);
            if (nextId != id) {
                return nextId;
            }
        }
    }


    private Properties createProperties() {
        Properties producerProperties = new Properties();
        producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return producerProperties;
    }
}
