package com.agacostays.gateway.util;

import com.agacostays.gateway.constants.GatewayConstants;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.UUID;

public final class TraceIdUtil {

    private TraceIdUtil() {}

    public static String resolveTraceId(ServerHttpRequest request) {
        String incoming = request.getHeaders().getFirst(GatewayConstants.DEFAULT_TRACE_ID_HEADER);
        if (incoming == null || incoming.isBlank()) {
            incoming = request.getHeaders().getFirst(GatewayConstants.CORRELATION_ID_HEADER);
        }
        return incoming == null || incoming.isBlank()
                ? UUID.randomUUID().toString()
                : incoming;
    }
}
