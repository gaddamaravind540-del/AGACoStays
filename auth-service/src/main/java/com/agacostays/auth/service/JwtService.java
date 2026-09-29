package com.agacostays.auth.service;

import com.agacostays.auth.entity.User;
import com.agacostays.auth.security.JwtClaims;

public interface JwtService {
    String generateAccessToken(User user);
    JwtClaims parseAndValidate(String token);
    long getAccessTokenExpirationSeconds();
}
