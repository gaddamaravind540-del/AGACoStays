package com.agacostays.booking.producer;

import com.agacostays.booking.constants.KafkaTopicConstants;
import com.agacostays.booking.entity.Booking;
import com.agacostays.booking.event.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class BookingEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public BookingEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void bookingCreated(Booking b) {
        kafkaTemplate.send(KafkaTopicConstants.BOOKING_CREATED, String.valueOf(b.getBookingId()),
                new BookingCreatedEvent(b.getBookingId(), b.getBranchId(), b.getCustomerId(), b.getRoomId(), b.getRoomCharges()));
    }

    public void bookingApproved(Booking b) {
        kafkaTemplate.send(KafkaTopicConstants.BOOKING_APPROVED, String.valueOf(b.getBookingId()),
                new BookingApprovedEvent(b.getBookingId(), b.getBranchId(), b.getCustomerId(), b.getRoomId()));
    }

    public void bookingRejected(Booking b, String remarks) {
        kafkaTemplate.send(KafkaTopicConstants.BOOKING_REJECTED, String.valueOf(b.getBookingId()),
                new BookingRejectedEvent(b.getBookingId(), b.getBranchId(), b.getCustomerId(), b.getRoomId(), remarks));
    }

    public void bookingCancelled(Booking b, String reason) {
        kafkaTemplate.send(KafkaTopicConstants.BOOKING_CANCELLED, String.valueOf(b.getBookingId()),
                new BookingCancelledEvent(b.getBookingId(), b.getBranchId(), b.getCustomerId(), b.getRoomId(), reason));
    }

    public void checkInCompleted(Booking b) {
        kafkaTemplate.send(KafkaTopicConstants.CHECKIN_COMPLETED, String.valueOf(b.getBookingId()),
                new CheckInCompletedEvent(b.getBookingId(), b.getBranchId(), b.getCustomerId(), b.getRoomId()));
    }

    public void checkoutCompleted(Booking b) {
        kafkaTemplate.send(KafkaTopicConstants.CHECKOUT_COMPLETED, String.valueOf(b.getBookingId()),
                new CheckoutCompletedEvent(b.getBookingId(), b.getBranchId(), b.getCustomerId(), b.getRoomId()));
    }

    public void paymentStatusUpdated(Booking b) {
        kafkaTemplate.send(KafkaTopicConstants.BOOKING_PAYMENT_STATUS_UPDATED, String.valueOf(b.getBookingId()),
                new BookingPaymentStatusUpdatedEvent(b.getBookingId(), b.getBranchId(), b.getCustomerId(), b.getPaymentStatus()));
    }

    public void statusChanged(Booking b) {
        kafkaTemplate.send(KafkaTopicConstants.BOOKING_STATUS_CHANGED, String.valueOf(b.getBookingId()),
                new BookingStatusChangedEvent(b.getBookingId(), b.getBranchId(), b.getCustomerId(), b.getRoomId(), b.getBookingStatus()));
    }
}
