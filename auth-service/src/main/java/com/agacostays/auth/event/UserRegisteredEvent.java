package com.agacostays.auth.event;

import java.time.Instant;

public record UserRegisteredEvent(
        String eventId,
        Long userId,
        String fullName,
        String email,
        String roleName,
        Instant occurredAt
) {}
