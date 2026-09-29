package com.agacostays.auth.dto.response;

public record AuthResponse(
        UserAuthResponse user,
        TokenResponse tokens
) {}
