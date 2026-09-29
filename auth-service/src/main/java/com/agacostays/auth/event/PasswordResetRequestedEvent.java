package com.agacostays.auth.event;

import java.time.Instant;

public record PasswordResetRequestedEvent(
        String eventId,
        Long userId,
        String email,
        String resetToken,
        Instant occurredAt
) {}
