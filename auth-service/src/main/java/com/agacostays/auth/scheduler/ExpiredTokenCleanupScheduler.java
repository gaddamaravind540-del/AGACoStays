package com.agacostays.auth.scheduler;

import com.agacostays.auth.enums.PasswordResetStatus;
import com.agacostays.auth.enums.TokenStatus;
import com.agacostays.auth.repository.OtpTokenRepository;
import com.agacostays.auth.repository.PasswordResetTokenRepository;
import com.agacostays.auth.repository.RefreshTokenRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ExpiredTokenCleanupScheduler {

    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final OtpTokenRepository otpTokenRepository;

    public ExpiredTokenCleanupScheduler(
            RefreshTokenRepository refreshTokenRepository,
            PasswordResetTokenRepository passwordResetTokenRepository,
            OtpTokenRepository otpTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.otpTokenRepository = otpTokenRepository;
    }

    @Scheduled(cron = "${app.cleanup.cron:0 0/30 * * * *}")
    public void cleanupExpiredTokens() {
        LocalDateTime now = LocalDateTime.now();
        refreshTokenRepository.deleteByExpiresAtBefore(now);
        passwordResetTokenRepository.deleteByExpiresAtBefore(now);
        otpTokenRepository.deleteByExpiresAtBefore(now);
    }
}
