package com.agacostays.auth.event;

import java.time.Instant;

public record LoginSuccessEvent(
        String eventId,
        Long userId,
        String email,
        String roleName,
        String ipAddress,
        Instant occurredAt
) {}
