package com.agacostays.support.producer;

import com.agacostays.support.constants.KafkaTopicConstants;
import com.agacostays.support.entity.*;
import com.agacostays.support.event.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class SupportFeedbackEventProducer {

    private final KafkaTemplate<String,Object> kafka;

    public SupportFeedbackEventProducer(KafkaTemplate<String,Object> kafka) {
        this.kafka=kafka;
    }

    public void supportCreated(CustomerSupportRequest r) {
        kafka.send(KafkaTopicConstants.SUPPORT_REQUEST_CREATED, String.valueOf(r.getRequestId()),
                new SupportRequestCreatedEvent(r.getRequestId(),r.getBranchId(),r.getCustomerId(),
                        r.getBookingId(),r.getPriority().name()));
    }

    public void supportStatusChanged(CustomerSupportRequest r, Object oldStatus, String remarks) {
        kafka.send(KafkaTopicConstants.SUPPORT_STATUS_CHANGED, String.valueOf(r.getRequestId()),
                new SupportStatusChangedEvent(r.getRequestId(),r.getBranchId(),r.getCustomerId(),r.getStatus().name()));
    }

    public void feedbackSubmitted(Feedback f) {
        kafka.send(KafkaTopicConstants.FEEDBACK_SUBMITTED, String.valueOf(f.getFeedbackId()),
                new FeedbackSubmittedEvent(f.getFeedbackId(),f.getBranchId(),f.getBookingId(),f.getCustomerId()));
    }

    public void restaurantFeedbackSubmitted(RestaurantFeedback f) {
        kafka.send(KafkaTopicConstants.RESTAURANT_FEEDBACK_SUBMITTED, String.valueOf(f.getFeedbackId()),
                new RestaurantFeedbackSubmittedEvent(f.getFeedbackId(),f.getBranchId(),f.getBookingId(),
                        f.getOrderId(),f.getCustomerId()));
    }
}
