package com.agacostays.restaurant.consumer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
@Component
public class PaymentSuccessEventConsumer.java {
    @KafkaListener(topics="payment-success", groupId="restaurant-service")
    public void handle(String message) {
        // Process the agreed upstream event contract idempotently.
    }
}
