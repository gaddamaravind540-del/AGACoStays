package com.agacostays.room.util;

import java.math.BigDecimal;

public final class RoomPriceUtil {
    private RoomPriceUtil() {}

    public static void validate(BigDecimal price) {
        if (price == null || price.signum() <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero");
        }
    }
}
