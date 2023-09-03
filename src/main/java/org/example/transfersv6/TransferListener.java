package org.example.transfersv6;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import static org.example.transfersv6.Actor.send;
import static org.example.transfersv6.Utils.TRANSFERS_TOPIC;

public class TransferListener implements Runnable {

    private static final UUID APPLICATION_ID = UUID.randomUUID();
    private final KafkaConsumer<String, String> consumer = createConsumer();

    public void run() {
        while (true) {
            ConsumerRecords<String, String> poll = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : poll) {
                String[] tuple = record.value().split("\\|");
                String from = tuple[0];
                Transfer transfer = new Transfer(from, tuple[1], Integer.parseInt(tuple[2]));
                send(from, transfer);
            }
        }
    }

    private KafkaConsumer<String, String> createConsumer() {
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(createProperties());
        consumer.subscribe(List.of(TRANSFERS_TOPIC));
        return consumer;
    }

    private Properties createProperties() {
        Properties consumerProperties = new Properties();
        consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, APPLICATION_ID.toString());
        consumerProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return consumerProperties;
    }
}
