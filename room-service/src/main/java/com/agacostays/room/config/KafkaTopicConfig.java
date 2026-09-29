package com.agacostays.room.config;

import com.agacostays.room.constants.KafkaTopicConstants;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {
    @Bean
    NewTopic roomEventsTopic() {
        return new NewTopic(KafkaTopicConstants.ROOM_EVENTS, 3, (short) 1);
    }
}
