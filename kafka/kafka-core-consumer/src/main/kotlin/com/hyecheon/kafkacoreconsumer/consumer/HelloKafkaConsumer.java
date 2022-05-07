package com.hyecheon.kafkacoreconsumer.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2022/05/07
 */
@Service
public class HelloKafkaConsumer {

    @KafkaListener(topics = "t-hello")
    public void consume(String message) {
        System.out.println(message);
    }

}