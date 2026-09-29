package com.agacostays.auth.service;

import com.agacostays.auth.dto.request.*;
import com.agacostays.auth.dto.response.AuthResponse;
import com.agacostays.auth.dto.response.UserAuthResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request, String ipAddress, String userAgent);

    void logout(LogoutRequest request, Long currentUserId);

    UserAuthResponse validateToken(ValidateTokenRequest request);

    UserAuthResponse currentUser(Long userId);
}
