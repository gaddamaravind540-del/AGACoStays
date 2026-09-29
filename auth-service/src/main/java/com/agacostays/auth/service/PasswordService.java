package com.agacostays.auth.service;

import com.agacostays.auth.dto.request.ChangePasswordRequest;
import com.agacostays.auth.dto.request.ForgotPasswordRequest;
import com.agacostays.auth.dto.request.ResetPasswordRequest;
import com.agacostays.auth.dto.response.PasswordResetResponse;

public interface PasswordService {
    PasswordResetResponse forgotPassword(ForgotPasswordRequest request);
    void resetPassword(ResetPasswordRequest request);
    void changePassword(Long userId, ChangePasswordRequest request);
}
