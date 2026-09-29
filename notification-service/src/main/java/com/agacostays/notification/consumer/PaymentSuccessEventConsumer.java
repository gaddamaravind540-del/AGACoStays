package com.agacostays.notification.consumer;

import com.agacostays.notification.dto.request.SendEmailRequest;
import com.agacostays.notification.enums.NotificationType;
import com.agacostays.notification.service.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PaymentSuccessEventConsumer {
    private final NotificationService service;
    public PaymentSuccessEventConsumer(NotificationService service){this.service=service;}
    @KafkaListener(topics="payment.success", groupId="${spring.kafka.consumer.group-id}")
    public void consume(String payload) {
        // Event payload contracts are owned by the producer service. This consumer preserves the event-to-notification boundary;
        // detailed recipient resolution can be enriched through Booking/User/Branch service clients as those contracts are finalized.
        // A safe placeholder recipient prevents accidental real-mail delivery during local development.
        service.sendEmail(new SendEmailRequest(null,null,null,"customer@placeholder.invalid","PAYMENT_SUCCESS","Payment Successful",payload,NotificationType.PAYMENT_SUCCESS,Map.of("eventPayload",payload)));
    }
}
