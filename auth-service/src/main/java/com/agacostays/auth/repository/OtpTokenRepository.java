package com.agacostays.auth.repository;

import com.agacostays.auth.entity.OtpToken;
import com.agacostays.auth.enums.OtpPurpose;
import com.agacostays.auth.enums.TokenStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public interface OtpTokenRepository extends JpaRepository<OtpToken, Long> {
    Optional<OtpToken> findTopByDestinationAndPurposeAndOtpHashAndStatusOrderByCreatedAtDesc(
            String destination, OtpPurpose purpose, String otpHash, TokenStatus status);

    @Transactional
    long deleteByExpiresAtBefore(LocalDateTime cutoff);
}
