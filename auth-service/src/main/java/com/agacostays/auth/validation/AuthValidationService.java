package com.agacostays.auth.validation;

import com.agacostays.auth.dto.request.*;
import org.springframework.stereotype.Component;

@Component
public class AuthValidationService {

    public void validateRegister(RegisterRequest request) {
        if (request.password().contains(request.email().split("@")[0])) {
            throw new IllegalArgumentException("Password should not contain the email local-part");
        }
    }

    public void validateNewPassword(ChangePasswordRequest request) {
        if (request.currentPassword().equals(request.newPassword())) {
            throw new IllegalArgumentException("New password must be different from current password");
        }
    }

    public void validateRefreshToken(RefreshTokenRequest request) {
        if (request.refreshToken().isBlank()) {
            throw new IllegalArgumentException("Refresh token cannot be blank");
        }
    }
}
