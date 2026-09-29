package com.agacostays.notification.consumer;

import com.agacostays.notification.dto.request.SendEmailRequest;
import com.agacostays.notification.enums.NotificationType;
import com.agacostays.notification.service.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CheckoutCompletedEventConsumer {
    private final NotificationService service;
    public CheckoutCompletedEventConsumer(NotificationService service){this.service=service;}
    @KafkaListener(topics="checkout.completed", groupId="${spring.kafka.consumer.group-id}")
    public void consume(String payload) {
        // Event payload contracts are owned by the producer service. This consumer preserves the event-to-notification boundary;
        // detailed recipient resolution can be enriched through Booking/User/Branch service clients as those contracts are finalized.
        // A safe placeholder recipient prevents accidental real-mail delivery during local development.
        service.sendEmail(new SendEmailRequest(null,null,null,"customer@placeholder.invalid","CHECKOUT_REMINDER","Checkout Completed",payload,NotificationType.CHECKOUT_REMINDER,Map.of("eventPayload",payload)));
    }
}
