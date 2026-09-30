package com.agacostays.restaurant.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentSuccessEventConsumer {

    @KafkaListener(
            topics = "payment-success",
            groupId = "restaurant-service"
    )
    public void handle(String message) {

        // Process the agreed upstream event contract idempotently.

        System.out.println(
                "Payment success event received: "
                        + message
        );
    }
}