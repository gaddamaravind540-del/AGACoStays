package com.agacostays.auth.service.impl;

import com.agacostays.auth.constants.JwtConstants;
import com.agacostays.auth.entity.User;
import com.agacostays.auth.exception.InvalidTokenException;
import com.agacostays.auth.security.JwtClaims;
import com.agacostays.auth.service.JwtService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Base64;

@Service
public class JwtServiceImpl implements JwtService {

    private final SecretKey signingKey;
    private final long accessTokenExpirationSeconds;

    public JwtServiceImpl(
            @Value("${app.jwt.secret}") String base64Secret,
            @Value("${app.jwt.access-token-expiration-seconds:900}") long accessTokenExpirationSeconds) {

        byte[] keyBytes = Base64.getDecoder().decode(base64Secret);
        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpirationSeconds = accessTokenExpirationSeconds;
    }

    @Override
    public String generateAccessToken(User user) {
        Instant now = Instant.now();
        Instant expires = now.plusSeconds(accessTokenExpirationSeconds);

        List<String> roles = List.of(user.getRole().getRoleName().toUpperCase(Locale.ROOT));

        return Jwts.builder()
                .subject(String.valueOf(user.getUserId()))
                .claim(JwtConstants.CLAIM_USER_ID, user.getUserId())
                .claim(JwtConstants.CLAIM_FULL_NAME, user.getFullName())
                .claim(JwtConstants.CLAIM_EMAIL, user.getEmail())
                .claim(JwtConstants.CLAIM_ROLES, roles)
                .claim(JwtConstants.CLAIM_ROLE, user.getRole().getRoleName())
                .claim(JwtConstants.CLAIM_USER_TYPE, user.getUserType().name())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expires))
                .signWith(signingKey, Jwts.SIG.HS256)
                .compact();
    }

    @Override
    public JwtClaims parseAndValidate(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(signingKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            Long userId = parseUserId(claims.get(JwtConstants.CLAIM_USER_ID));
            String email = claims.get(JwtConstants.CLAIM_EMAIL, String.class);
            String fullName = claims.get(JwtConstants.CLAIM_FULL_NAME, String.class);
            String role = claims.get(JwtConstants.CLAIM_ROLE, String.class);
            String userType = claims.get(JwtConstants.CLAIM_USER_TYPE, String.class);

            return new JwtClaims(userId, email, fullName, role, userType, claims.getExpiration().toInstant());
        } catch (ExpiredJwtException ex) {
            throw new com.agacostays.auth.exception.TokenExpiredException("JWT token has expired");
        } catch (JwtException | IllegalArgumentException ex) {
            throw new InvalidTokenException("JWT token is invalid");
        }
    }

    private Long parseUserId(Object raw) {
        if (raw instanceof Number number) {
            return number.longValue();
        }
        if (raw == null) {
            throw new InvalidTokenException("JWT does not contain userId");
        }
        try {
            return Long.parseLong(String.valueOf(raw));
        } catch (NumberFormatException ex) {
            throw new InvalidTokenException("JWT userId is invalid");
        }
    }

    @Override
    public long getAccessTokenExpirationSeconds() {
        return accessTokenExpirationSeconds;
    }
}
