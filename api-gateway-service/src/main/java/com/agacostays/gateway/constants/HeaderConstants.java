package com.agacostays.gateway.constants;

public final class HeaderConstants {
    public static final String INTERNAL_AUTHENTICATED = "X-Gateway-Authenticated";
    public static final String RATE_LIMIT_REMAINING = "X-RateLimit-Remaining";
    public static final String RATE_LIMIT_REPLENISH = "X-RateLimit-Replenish-Rate";

    private HeaderConstants() {}
}
