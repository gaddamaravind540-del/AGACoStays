package com.agacostays.booking.util;

import com.agacostays.booking.enums.BookingStatus;
import com.agacostays.booking.exception.InvalidBookingStatusException;

public final class BookingStatusUtil {
    private BookingStatusUtil() {}

    public static void require(BookingStatus actual, BookingStatus... allowed) {
        for (BookingStatus status : allowed) {
            if (actual == status) return;
        }
        throw new InvalidBookingStatusException("Action is not allowed from status " + actual);
    }
}
