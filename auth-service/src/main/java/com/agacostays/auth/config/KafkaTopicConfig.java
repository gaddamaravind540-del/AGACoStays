package com.agacostays.auth.config;

import com.agacostays.auth.constants.KafkaTopicConstants;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    NewTopic userRegisteredTopic() {
        return TopicBuilder.name(KafkaTopicConstants.USER_REGISTERED).partitions(3).replicas(1).build();
    }

    @Bean
    NewTopic loginSuccessTopic() {
        return TopicBuilder.name(KafkaTopicConstants.LOGIN_SUCCESS).partitions(3).replicas(1).build();
    }

    @Bean
    NewTopic loginFailedTopic() {
        return TopicBuilder.name(KafkaTopicConstants.LOGIN_FAILED).partitions(3).replicas(1).build();
    }

    @Bean
    NewTopic passwordResetTopic() {
        return TopicBuilder.name(KafkaTopicConstants.PASSWORD_RESET_REQUESTED).partitions(3).replicas(1).build();
    }
}
