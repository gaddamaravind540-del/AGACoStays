package com.agacostays.room.util;

import java.time.LocalDate;

public final class DateRangeUtil {
    private DateRangeUtil() {}

    public static void validate(LocalDate checkIn, LocalDate checkOut) {
        if (checkIn == null || checkOut == null || !checkOut.isAfter(checkIn)) {
            throw new IllegalArgumentException("checkOutDate must be after checkInDate");
        }
    }
}
