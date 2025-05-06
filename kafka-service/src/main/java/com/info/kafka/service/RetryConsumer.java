package com.info.kafka.service;

import com.info.dto.constants.Constants;
import com.info.dto.kafka.EmailData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class RetryConsumer {

    private static final Logger logger = LoggerFactory.getLogger(RetryConsumer.class);

    @Autowired
    private KafkaTemplate<String, EmailData> kafkaTemplate;

    @KafkaListener(topics = Constants.TOPIC_EMAIL_RETRY_5S, groupId = Constants.KAFKA_GROUP_EMAIL_RETRY_5S, containerFactory = Constants.KAFKA_LISTENER_CONTAINER_FACTORY)
    public void retry5s(EmailData emailData) throws InterruptedException {
        logger.info("Retrying after 5s delay: " + emailData);
        Thread.sleep(5000); // simulate delay
        kafkaTemplate.send(Constants.TOPIC_EMAIL_RETRY_30S, emailData); // escalate retry
    }

    @KafkaListener(topics = Constants.TOPIC_EMAIL_RETRY_30S, groupId = Constants.KAFKA_GROUP_EMAIL_RETRY_30S, containerFactory = Constants.KAFKA_LISTENER_CONTAINER_FACTORY)
    public void retry30s(EmailData emailData) {
        logger.info("Retrying after 30s delay: " + emailData);
        // Final retry attempt — if fails again, go to DLQ
        if ("Apple".equals(emailData.getAppName())) {
            throw new RuntimeException("Final retry failed, will go to DLQ");
        }
    }
}
