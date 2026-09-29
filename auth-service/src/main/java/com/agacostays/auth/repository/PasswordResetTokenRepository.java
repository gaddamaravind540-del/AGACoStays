package com.agacostays.auth.repository;

import com.agacostays.auth.entity.PasswordResetToken;
import com.agacostays.auth.enums.PasswordResetStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByTokenHash(String tokenHash);

    @Transactional
    long deleteByExpiresAtBefore(LocalDateTime cutoff);

    @Transactional
    long deleteByStatusAndExpiresAtBefore(PasswordResetStatus status, LocalDateTime cutoff);
}
