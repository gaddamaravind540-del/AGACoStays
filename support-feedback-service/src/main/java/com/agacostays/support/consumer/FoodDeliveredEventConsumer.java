package com.agacostays.support.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class FoodDeliveredEventConsumer {

    @KafkaListener(
            topics = "food-delivered",
            groupId = "support-feedback-service"
    )
    public void handle(String message) {

        // Event contract is intentionally kept
        // at the shared Kafka boundary.
        // Production handlers should be idempotent.

        System.out.println(
                "Food delivered event received: "
                        + message
        );
    }
}