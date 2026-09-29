package com.agacostays.auth.dto.response;

import java.time.Instant;

public record OtpResponse(
        String destination,
        String purpose,
        Instant expiresAt,
        String developmentOtp
) {}
