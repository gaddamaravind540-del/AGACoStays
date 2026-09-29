package com.agacostays.auth.mapper;

import com.agacostays.auth.dto.response.OtpResponse;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class OtpMapper {

    public OtpResponse map(String destination, String purpose, Instant expiresAt, String developmentOtp) {
        return new OtpResponse(destination, purpose, expiresAt, developmentOtp);
    }
}
