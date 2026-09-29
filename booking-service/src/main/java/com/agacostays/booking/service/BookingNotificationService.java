package com.agacostays.booking.service;

import com.agacostays.booking.entity.Booking;

public interface BookingNotificationService {
    void notifyCreated(Booking booking);
    void notifyStatusChanged(Booking booking);
}
