package com.agacostays.auth.event;

import java.time.Instant;

public record LoginFailedEvent(
        String eventId,
        String email,
        String reason,
        String ipAddress,
        Instant occurredAt
) {}
