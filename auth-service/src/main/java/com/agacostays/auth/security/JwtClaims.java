package com.agacostays.auth.security;

import java.time.Instant;

public record JwtClaims(
        Long userId,
        String email,
        String fullName,
        String role,
        String userType,
        Instant expiresAt
) {}
