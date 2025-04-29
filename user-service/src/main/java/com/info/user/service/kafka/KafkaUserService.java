package com.info.user.service.kafka;

import com.info.dto.constants.Constants;
import com.info.dto.kafka.EmailData;
import com.info.dto.user.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class KafkaUserService {

    @Autowired
    @Qualifier("emailDataKafkaTemplate")
    private KafkaTemplate<String, EmailData> emailDataKafkaTemplate;

    public void sendEmailNotification(UserDTO dto) {
        EmailData emailData = new EmailData(Constants.CACHE_NAME_USER, dto.getEmail(), Constants.USER_CREATED_SUCCESSFULLY);
        ProducerRecord<String, EmailData> record = new ProducerRecord<>(Constants.TOPIC_EMAIL, emailData);
        String correlationId = MDC.get(Constants.CORRELATION_ID);
        log.info("Processing request with correlationId={}", correlationId);
        record.headers().add(Constants.CORRELATION_ID, correlationId.getBytes(StandardCharsets.UTF_8));
        emailDataKafkaTemplate.send(record);
    }
}
