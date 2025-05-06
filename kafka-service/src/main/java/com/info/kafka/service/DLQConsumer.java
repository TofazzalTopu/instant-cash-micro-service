package com.info.kafka.service;

import com.info.dto.constants.Constants;
import com.info.dto.kafka.EmailData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class DLQConsumer {
    private static final Logger logger = LoggerFactory.getLogger(DLQConsumer.class);

    @KafkaListener(topics = Constants.TOPIC_EMAIL_RETRY_30S_DLQ, groupId = Constants.KAFKA_GROUP_EMAIL_RETRY_DQL)
    public void handleDLQ(EmailData emailData) {
        logger.info("DLQ received failed message: " + emailData);
        // Save to DB, alert, etc.
    }
}
