package com.agacostays.auth.dto.response;

public record UserAuthResponse(
        Long userId,
        String fullName,
        String email,
        String phone,
        String roleName,
        String userType,
        String status
) {}
