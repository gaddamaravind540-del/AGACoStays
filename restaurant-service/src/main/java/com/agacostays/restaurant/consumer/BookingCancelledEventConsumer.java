package com.agacostays.restaurant.consumer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
@Component
public class BookingCancelledEventConsumer.java {
    @KafkaListener(topics="booking-cancelled", groupId="restaurant-service")
    public void handle(String message) {
        // Process the agreed upstream event contract idempotently.
    }
}
