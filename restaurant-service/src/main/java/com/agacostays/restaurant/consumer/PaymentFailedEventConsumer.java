package com.agacostays.restaurant.consumer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
@Component
public class PaymentFailedEventConsumer.java {
    @KafkaListener(topics="payment-failed", groupId="restaurant-service")
    public void handle(String message) {
        // Process the agreed upstream event contract idempotently.
    }
}
