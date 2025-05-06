package com.info.kafka.service;

import com.info.dto.constants.Constants;
import com.info.dto.kafka.EmailData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessageService {

    private static Logger logger = LoggerFactory.getLogger(KafkaMessageService.class);
    @Autowired
    private KafkaTemplate<String, EmailData> kafkaTemplate;

    // Listen or consume from the topic: TOPIC_EMAIL
//    @KafkaListener(topics = Constants.TOPIC_EMAIL, groupId = Constants.KAFKA_GROUP_EMAIL, containerFactory = Constants.KAFKA_LISTENER_CONTAINER_FACTORY_EMAIL_DATA)
    public void consume(EmailData emailData) {
        logger.info("Consumed Message: {} from the topic: {} and group id: {}", emailData, Constants.TOPIC_EMAIL, Constants.KAFKA_GROUP_EMAIL);
    }

    // Listen or consume from the topic: TOPIC_SMS
    @KafkaListener(topics = Constants.TOPIC_SMS, groupId = Constants.KAFKA_GROUP_EMAIL, containerFactory = Constants.KAFKA_LISTENER_CONTAINER_FACTORY_STRING)
    public void listener(String message) {
        logger.info("Consumed Message: {} from the topic: {} and group id: {}", message, Constants.TOPIC_SMS, Constants.KAFKA_GROUP_EMAIL);
    }

    @KafkaListener(topics = Constants.TOPIC_SMS_OUTPUT, groupId = Constants.KAFKA_GROUP_EMAIL, containerFactory = Constants.KAFKA_LISTENER_CONTAINER_FACTORY_STRING)
    public void outputListener(String message) {
        logger.info("Consumed output Message: {} from the topic: {} and group id: {}", message, Constants.TOPIC_SMS_OUTPUT, Constants.KAFKA_GROUP_EMAIL);
    }


    @KafkaListener(topics = Constants.TOPIC_EMAIL, groupId = Constants.KAFKA_GROUP_EMAIL, containerFactory = Constants.KAFKA_LISTENER_CONTAINER_FACTORY)
    public void listen(EmailData emailData, @Header(Constants.CORRELATION_ID) String correlationId) {
        MDC.put(Constants.CORRELATION_ID, correlationId);
        logger.info("Received message with correlation ID: {}", correlationId);
        MDC.clear();
    }

    @KafkaListener(topics = Constants.TOPIC_EMAIL, groupId = Constants.KAFKA_GROUP_EMAIL, containerFactory = Constants.KAFKA_LISTENER_CONTAINER_FACTORY)
    public void consume(EmailData emailData, @Header(Constants.CORRELATION_ID) String correlationId, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        MDC.put(Constants.CORRELATION_ID, correlationId);
        logger.info("Received message with correlation ID: {}", correlationId);
        if ("Apple".equals(emailData.getAppName())) {
            // Simulate failure
            System.out.println("Failing message, sending to retry.5s...");
            kafkaTemplate.send(Constants.TOPIC_EMAIL_RETRY_5S, emailData);
            throw new RuntimeException("Temporary failure");
        }
        MDC.clear();
    }

}
