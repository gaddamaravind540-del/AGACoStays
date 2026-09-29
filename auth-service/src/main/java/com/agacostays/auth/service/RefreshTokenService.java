package com.agacostays.auth.service;

import com.agacostays.auth.dto.request.RefreshTokenRequest;
import com.agacostays.auth.entity.User;

public interface RefreshTokenService {
    String create(User user);
    User validateAndGetUser(String refreshToken);
    void revoke(String refreshToken);
}
