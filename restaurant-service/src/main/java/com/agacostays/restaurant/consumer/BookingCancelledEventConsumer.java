package com.agacostays.restaurant.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class BookingCancelledEventConsumer {

    @KafkaListener(
            topics = "booking-cancelled",
            groupId = "restaurant-service"
    )
    public void handle(String message) {

        System.out.println(
                "Booking cancelled event received: "
                        + message
        );
    }
}