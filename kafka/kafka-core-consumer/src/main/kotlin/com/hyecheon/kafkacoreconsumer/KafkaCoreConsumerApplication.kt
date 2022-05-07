package com.hyecheon.kafkacoreconsumer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KafkaCoreConsumerApplication

fun main(args: Array<String>) {
	runApplication<KafkaCoreConsumerApplication>(*args)
}
