package com.agacostays.booking.service.impl;

import com.agacostays.booking.client.NotificationServiceClient;
import com.agacostays.booking.entity.Booking;
import com.agacostays.booking.service.BookingNotificationService;
import org.springframework.stereotype.Service;

@Service
public class BookingNotificationServiceImpl implements BookingNotificationService {

    @SuppressWarnings("unused")
    private final NotificationServiceClient notificationServiceClient;

    public BookingNotificationServiceImpl(NotificationServiceClient notificationServiceClient) {
        this.notificationServiceClient = notificationServiceClient;
    }

    @Override
    public void notifyCreated(Booking booking) {
        // Notification is intentionally event-driven; Notification service consumes booking-created.
    }

    @Override
    public void notifyStatusChanged(Booking booking) {
        // Notification is intentionally event-driven; Notification service consumes booking status events.
    }
}
