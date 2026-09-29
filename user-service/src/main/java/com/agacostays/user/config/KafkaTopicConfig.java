package com.agacostays.user.config;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.*;
import org.springframework.kafka.config.TopicBuilder;
@Configuration
public class KafkaTopicConfig {
 @Bean NewTopic userChangedTopic(){return TopicBuilder.name("user.events").partitions(3).replicas(1).build();}
}
