package com.agacostays.booking.validation;

import com.agacostays.booking.entity.Booking;
import com.agacostays.booking.enums.BookingStatus;
import com.agacostays.booking.exception.InvalidBookingStatusException;
import org.springframework.stereotype.Service;

@Service
public class BookingStatusValidationService {
    public void requireStatus(Booking booking, BookingStatus... allowed) {
        for (BookingStatus s : allowed) {
            if (booking.getBookingStatus() == s) return;
        }
        throw new InvalidBookingStatusException(
                "Booking " + booking.getBookingId() + " is in status " + booking.getBookingStatus());
    }
}
