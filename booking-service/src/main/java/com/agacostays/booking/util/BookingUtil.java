package com.agacostays.booking.util;

import java.time.LocalDate;

public final class BookingUtil {
    private BookingUtil() {}
    public static boolean validDates(LocalDate in, LocalDate out) {
        return in != null && out != null && out.isAfter(in);
    }
}
