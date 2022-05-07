package com.hyecheon.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2022/05/07
 */
@Service
public class HelloKafkaConsumer {


    @KafkaListener(topics = "t-hello")
    public void consumer(String message) {
        System.out.println(message);
    }

}
