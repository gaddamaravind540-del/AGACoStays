package com.agacostays.booking.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentSuccessEventConsumer.java {

    @KafkaListener(topics = "payment-success", groupId = "booking-service")
    public void handle(String message) {
        // Event contract is consumed here after the upstream service finalizes its schema.
        // Keep processing idempotent in the production implementation.
    }
}
