package com.agacostays.booking.constants;

public final class KafkaTopicConstants {
    private KafkaTopicConstants() {}
    public static final String BOOKING_CREATED = "booking-created";
    public static final String BOOKING_APPROVED = "booking-approved";
    public static final String BOOKING_REJECTED = "booking-rejected";
    public static final String BOOKING_CANCELLED = "booking-cancelled";
    public static final String CHECKIN_COMPLETED = "checkin-completed";
    public static final String CHECKOUT_COMPLETED = "checkout-completed";
    public static final String BOOKING_PAYMENT_STATUS_UPDATED = "booking-payment-status-updated";
    public static final String BOOKING_STATUS_CHANGED = "booking-status-changed";
}
