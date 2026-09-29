package com.agacostays.notification.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Bean NewTopic notificationSentTopic() { return TopicBuilder.name("notification.sent").partitions(3).replicas(1).build(); }
    @Bean NewTopic notificationFailedTopic() { return TopicBuilder.name("notification.failed").partitions(3).replicas(1).build(); }
    @Bean NewTopic bookingCreatedTopic() { return TopicBuilder.name("booking.created").partitions(3).replicas(1).build(); }
    @Bean NewTopic checkoutCompletedTopic() { return TopicBuilder.name("checkout.completed").partitions(3).replicas(1).build(); }
    @Bean NewTopic paymentSuccessTopic() { return TopicBuilder.name("payment.success").partitions(3).replicas(1).build(); }
    @Bean NewTopic salaryCreditedTopic() { return TopicBuilder.name("salary.credited").partitions(3).replicas(1).build(); }
    @Bean NewTopic foodDeliveredTopic() { return TopicBuilder.name("food.delivered").partitions(3).replicas(1).build(); }
    @Bean NewTopic supportRequestCreatedTopic() { return TopicBuilder.name("support.request.created").partitions(3).replicas(1).build(); }
}
