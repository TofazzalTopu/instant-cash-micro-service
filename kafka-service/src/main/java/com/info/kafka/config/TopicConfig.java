package com.info.kafka.config;

import com.info.dto.constants.Constants;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

//@Configuration
public class TopicConfig {

//    @Bean
    public NewTopic orders() {
        return TopicBuilder.name(Constants.TOPIC_EMAIL)
                .partitions(3)      // Topic will have 3 partitions
                .compact()          // Log compaction enabled
                .build();
    }

}
