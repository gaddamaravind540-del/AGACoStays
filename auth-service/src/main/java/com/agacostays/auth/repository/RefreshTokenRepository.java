package com.agacostays.auth.repository;

import com.agacostays.auth.entity.RefreshToken;
import com.agacostays.auth.enums.TokenStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByTokenHash(String tokenHash);

    @Transactional
    long deleteByExpiresAtBefore(LocalDateTime cutoff);

    @Transactional
    long deleteByStatusAndExpiresAtBefore(TokenStatus status, LocalDateTime cutoff);
}
