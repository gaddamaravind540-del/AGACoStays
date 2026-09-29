package com.agacostays.auth.service;

import com.agacostays.auth.dto.request.SendOtpRequest;
import com.agacostays.auth.dto.request.VerifyOtpRequest;
import com.agacostays.auth.dto.response.OtpResponse;

public interface OtpService {
    OtpResponse send(SendOtpRequest request);
    void verify(VerifyOtpRequest request);
}
