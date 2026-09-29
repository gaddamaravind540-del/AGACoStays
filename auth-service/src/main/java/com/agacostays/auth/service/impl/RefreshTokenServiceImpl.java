package com.agacostays.auth.service.impl;

import com.agacostays.auth.entity.RefreshToken;
import com.agacostays.auth.entity.User;
import com.agacostays.auth.enums.TokenStatus;
import com.agacostays.auth.exception.RefreshTokenException;
import com.agacostays.auth.repository.RefreshTokenRepository;
import com.agacostays.auth.service.RefreshTokenService;
import com.agacostays.auth.util.TokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository repository;
    private final long refreshExpirationDays;

    public RefreshTokenServiceImpl(
            RefreshTokenRepository repository,
            @Value("${app.jwt.refresh-token-expiration-days:7}") long refreshExpirationDays) {
        this.repository = repository;
        this.refreshExpirationDays = refreshExpirationDays;
    }

    @Override
    @Transactional
    public String create(User user) {
        String rawToken = UUID.randomUUID() + "." + UUID.randomUUID();
        RefreshToken entity = RefreshToken.builder()
                .user(user)
                .tokenHash(TokenUtil.sha256(rawToken))
                .status(TokenStatus.ACTIVE)
                .expiresAt(LocalDateTime.now().plusDays(refreshExpirationDays))
                .createdAt(LocalDateTime.now())
                .build();

        repository.save(entity);
        return rawToken;
    }

    @Override
    @Transactional(readOnly = true)
    public User validateAndGetUser(String refreshToken) {
        RefreshToken entity = repository.findByTokenHash(TokenUtil.sha256(refreshToken))
                .orElseThrow(() -> new RefreshTokenException("Refresh token is invalid"));

        if (entity.getStatus() != TokenStatus.ACTIVE) {
            throw new RefreshTokenException("Refresh token is not active");
        }

        if (entity.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RefreshTokenException("Refresh token has expired");
        }

        return entity.getUser();
    }

    @Override
    @Transactional
    public void revoke(String refreshToken) {
        repository.findByTokenHash(TokenUtil.sha256(refreshToken)).ifPresent(entity -> {
            entity.setStatus(TokenStatus.REVOKED);
            entity.setRevokedAt(LocalDateTime.now());
            repository.save(entity);
        });
    }
}
