package com.info.kafka.service;

import com.info.kafka.config.KafkaConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {

    @Autowired
    private KafkaConfigProperties kafkaConfigProperties;

    public void printKafkaConfig() {
        System.out.println("Bootstrap Servers: " + kafkaConfigProperties.getBootstrapServers());
        System.out.println("Consumer Group ID: " + kafkaConfigProperties.getConsumer().getGroupId());
        System.out.println("Producer Acks: " + kafkaConfigProperties.getProducer().getAcks());
        System.out.println("Kafka Security Protocol: " + kafkaConfigProperties.getProperties().getSecurityProtocol());
    }
}

