package com.agacostays.auth.service.impl;

import com.agacostays.auth.dto.request.ChangePasswordRequest;
import com.agacostays.auth.dto.request.ForgotPasswordRequest;
import com.agacostays.auth.dto.request.ResetPasswordRequest;
import com.agacostays.auth.dto.response.PasswordResetResponse;
import com.agacostays.auth.entity.PasswordResetToken;
import com.agacostays.auth.entity.User;
import com.agacostays.auth.enums.PasswordResetStatus;
import com.agacostays.auth.exception.InvalidCredentialsException;
import com.agacostays.auth.exception.PasswordResetException;
import com.agacostays.auth.exception.UserNotFoundException;
import com.agacostays.auth.repository.PasswordResetTokenRepository;
import com.agacostays.auth.repository.UserRepository;
import com.agacostays.auth.service.PasswordService;
import com.agacostays.auth.util.TokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordServiceImpl implements PasswordService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final long resetExpirationMinutes;

    public PasswordServiceImpl(
            UserRepository userRepository,
            PasswordResetTokenRepository tokenRepository,
            PasswordEncoder passwordEncoder,
            @Value("${app.password-reset.expiration-minutes:15}") long resetExpirationMinutes) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.resetExpirationMinutes = resetExpirationMinutes;
    }

    @Override
    @Transactional
    public PasswordResetResponse forgotPassword(ForgotPasswordRequest request) {
        String email = request.email().trim().toLowerCase();

        User user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UserNotFoundException("User not found for this email"));

        String rawToken = UUID.randomUUID() + "." + UUID.randomUUID();

        PasswordResetToken entity = PasswordResetToken.builder()
                .user(user)
                .tokenHash(TokenUtil.sha256(rawToken))
                .status(PasswordResetStatus.ACTIVE)
                .expiresAt(LocalDateTime.now().plusMinutes(resetExpirationMinutes))
                .createdAt(LocalDateTime.now())
                .build();

        tokenRepository.save(entity);

        // The raw reset token is returned here for development/testing.
        // Production should send it only through the notification/email service.
        return new PasswordResetResponse(
                PasswordResetStatus.ACTIVE.name(),
                "Password reset token created",
                rawToken
        );
    }

    @Override
    @Transactional
    public void resetPassword(ResetPasswordRequest request) {
        PasswordResetToken token = tokenRepository.findByTokenHash(TokenUtil.sha256(request.resetToken()))
                .orElseThrow(() -> new PasswordResetException("Invalid password reset token"));

        if (token.getStatus() != PasswordResetStatus.ACTIVE) {
            throw new PasswordResetException("Password reset token is not active");
        }

        if (token.getExpiresAt().isBefore(LocalDateTime.now())) {
            token.setStatus(PasswordResetStatus.EXPIRED);
            tokenRepository.save(token);
            throw new PasswordResetException("Password reset token has expired");
        }

        User user = token.getUser();
        user.setPasswordHash(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);

        token.setStatus(PasswordResetStatus.USED);
        token.setUsedAt(LocalDateTime.now());
        tokenRepository.save(token);
    }

    @Override
    @Transactional
    public void changePassword(Long userId, ChangePasswordRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Authenticated user not found"));

        if (!passwordEncoder.matches(request.currentPassword(), user.getPasswordHash())) {
            throw new InvalidCredentialsException("Current password is incorrect");
        }

        if (request.currentPassword().equals(request.newPassword())) {
            throw new PasswordResetException("New password must differ from current password");
        }

        user.setPasswordHash(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);
    }
}
