package com.agacostays.booking.util;

import java.math.BigDecimal;

public final class BookingPriceUtil {
    private BookingPriceUtil() {}
    public static BigDecimal calculate(BigDecimal pricePerDay, int days) {
        return pricePerDay.multiply(BigDecimal.valueOf(days));
    }
}
