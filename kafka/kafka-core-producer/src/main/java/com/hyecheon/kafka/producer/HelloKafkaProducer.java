package com.hyecheon.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2022/05/07
 */
@Service
public class HelloKafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public HelloKafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendHello(String name) {
        kafkaTemplate.send("t-hello", "Hello " + name);
    }

}
