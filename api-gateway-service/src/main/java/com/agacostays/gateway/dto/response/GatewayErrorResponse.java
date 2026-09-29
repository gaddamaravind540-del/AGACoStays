package com.agacostays.gateway.dto.response;

import com.agacostays.gateway.enums.GatewayErrorCode;

import java.time.Instant;

public record GatewayErrorResponse(
        Instant timestamp,
        int status,
        String error,
        String message,
        GatewayErrorCode code,
        String path,
        String traceId
) {}
