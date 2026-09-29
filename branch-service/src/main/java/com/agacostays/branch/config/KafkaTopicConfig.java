package com.agacostays.branch.config;
import org.apache.kafka.clients.admin.NewTopic; import org.springframework.context.annotation.*;
import org.springframework.kafka.config.TopicBuilder;
@Configuration public class KafkaTopicConfig{
 @Bean NewTopic branchEvents(){return TopicBuilder.name("branch.events").partitions(3).replicas(1).build();}
}
