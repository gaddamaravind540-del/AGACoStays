package com.agacostays.booking.util;

public final class GuestIdProofUtil {
    private GuestIdProofUtil() {}
    public static String mask(String value) {
        if (value == null || value.length() < 4) return "****";
        return "****" + value.substring(value.length() - 4);
    }
}
