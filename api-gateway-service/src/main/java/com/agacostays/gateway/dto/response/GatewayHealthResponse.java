package com.agacostays.gateway.dto.response;

import java.time.Instant;

public record GatewayHealthResponse(
        String service,
        String status,
        Instant timestamp
) {}
