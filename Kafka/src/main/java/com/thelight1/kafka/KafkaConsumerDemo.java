package com.thelight1.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class KafkaConsumerDemo {
    private  final KafkaConsumer<String, String> consumer;

    private KafkaConsumerDemo(){
        Properties props = new Properties();
        props.put("bootstrap.servers", "127.0.0.1:9092;127.0.0.1:9093;127.0.0.1:9094");
        props.put("group.id", "GroupDemo");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumer = new KafkaConsumer<>(props);
    }

    void consume(){
        consumer.subscribe(Arrays.asList(KafkaProducerDemo.TOPIC));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records)
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
        }
    }

    public  static  void main(String[] args){

        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    new KafkaConsumerDemo().consume();
                }
            });
            thread.setDaemon(true);
            thread.start();
        }

        while (true) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
