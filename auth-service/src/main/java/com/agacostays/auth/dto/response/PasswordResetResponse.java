package com.agacostays.auth.dto.response;

public record PasswordResetResponse(
        String status,
        String message,
        String resetToken
) {}
