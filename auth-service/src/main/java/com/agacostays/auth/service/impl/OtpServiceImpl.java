package com.agacostays.auth.service.impl;

import com.agacostays.auth.dto.request.SendOtpRequest;
import com.agacostays.auth.dto.request.VerifyOtpRequest;
import com.agacostays.auth.dto.response.OtpResponse;
import com.agacostays.auth.entity.OtpToken;
import com.agacostays.auth.enums.OtpPurpose;
import com.agacostays.auth.enums.TokenStatus;
import com.agacostays.auth.exception.InvalidOtpException;
import com.agacostays.auth.mapper.OtpMapper;
import com.agacostays.auth.repository.OtpTokenRepository;
import com.agacostays.auth.service.OtpService;
import com.agacostays.auth.util.OtpUtil;
import com.agacostays.auth.util.TokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class OtpServiceImpl implements OtpService {

    private final OtpTokenRepository repository;
    private final OtpMapper mapper;
    private final long expirationMinutes;
    private final boolean exposeOtpForDevelopment;

    public OtpServiceImpl(
            OtpTokenRepository repository,
            OtpMapper mapper,
            @Value("${app.otp.expiration-minutes:5}") long expirationMinutes,
            @Value("${app.otp.expose-in-development:true}") boolean exposeOtpForDevelopment) {
        this.repository = repository;
        this.mapper = mapper;
        this.expirationMinutes = expirationMinutes;
        this.exposeOtpForDevelopment = exposeOtpForDevelopment;
    }

    @Override
    @Transactional
    public OtpResponse send(SendOtpRequest request) {
        String destination = request.destination().trim().toLowerCase();
        String otp = String.format("%06d", ThreadLocalRandom.current().nextInt(0, 1_000_000));

        OtpToken token = OtpToken.builder()
                .destination(destination)
                .otpHash(TokenUtil.sha256(otp))
                .purpose(request.purpose())
                .status(TokenStatus.ACTIVE)
                .expiresAt(LocalDateTime.now().plusMinutes(expirationMinutes))
                .createdAt(LocalDateTime.now())
                .build();

        repository.save(token);

        String developmentOtp = exposeOtpForDevelopment ? otp : null;

        return mapper.map(
                destination,
                request.purpose().name(),
                token.getExpiresAt().atZone(java.time.ZoneId.systemDefault()).toInstant(),
                developmentOtp
        );
    }

    @Override
    @Transactional
    public void verify(VerifyOtpRequest request) {
        String destination = request.destination().trim().toLowerCase();
        String hash = TokenUtil.sha256(request.otp());

        OtpToken token = repository
                .findTopByDestinationAndPurposeAndOtpHashAndStatusOrderByCreatedAtDesc(
                        destination,
                        request.purpose(),
                        hash,
                        TokenStatus.ACTIVE
                )
                .orElseThrow(() -> new InvalidOtpException("Invalid OTP"));

        if (token.getExpiresAt().isBefore(LocalDateTime.now())) {
            token.setStatus(TokenStatus.EXPIRED);
            repository.save(token);
            throw new InvalidOtpException("OTP has expired");
        }

        token.setStatus(TokenStatus.USED);
        token.setVerifiedAt(LocalDateTime.now());
        repository.save(token);
    }
}
