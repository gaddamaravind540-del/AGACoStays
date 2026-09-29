package com.agacostays.auth.mapper;

import com.agacostays.auth.dto.response.TokenResponse;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class TokenMapper {

    public TokenResponse map(String accessToken, String refreshToken, long expiresInSeconds, Instant issuedAt) {
        return new TokenResponse(accessToken, refreshToken, "Bearer", expiresInSeconds, issuedAt);
    }
}
