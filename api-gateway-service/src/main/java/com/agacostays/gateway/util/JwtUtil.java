package com.agacostays.gateway.util;

import com.agacostays.gateway.constants.GatewayConstants;

public final class JwtUtil {

    private JwtUtil() {}

    public static String extractBearerToken(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith(GatewayConstants.BEARER_PREFIX)) {
            return null;
        }
        return authorizationHeader.substring(GatewayConstants.BEARER_PREFIX.length()).trim();
    }
}
