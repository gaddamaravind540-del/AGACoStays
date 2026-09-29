package com.agacostays.booking.util;

import com.agacostays.booking.exception.InvalidDateRangeException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public final class DateRangeUtil {
    private DateRangeUtil() {}

    public static int numberOfDays(LocalDate checkIn, LocalDate checkOut) {
        if (checkIn == null || checkOut == null || !checkOut.isAfter(checkIn)) {
            throw new InvalidDateRangeException();
        }
        return Math.toIntExact(ChronoUnit.DAYS.between(checkIn, checkOut));
    }
}
