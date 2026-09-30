package com.agacostays.restaurant.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentFailedEventConsumer {

    @KafkaListener(
            topics = "payment-failed",
            groupId = "restaurant-service"
    )
    public void handle(String message) {

        System.out.println(
                "Payment failed event received: "
                        + message
        );
    }
}