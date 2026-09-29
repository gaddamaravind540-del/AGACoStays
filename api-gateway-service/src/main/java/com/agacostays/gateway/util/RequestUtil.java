package com.agacostays.gateway.util;

import org.springframework.web.server.ServerWebExchange;

public final class RequestUtil {

    private RequestUtil() {}

    public static String path(ServerWebExchange exchange) {
        return exchange.getRequest().getPath().value();
    }

    public static String method(ServerWebExchange exchange) {
        return exchange.getRequest().getMethod() == null
                ? "UNKNOWN"
                : exchange.getRequest().getMethod().name();
    }
}
