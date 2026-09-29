package com.agacostays.gateway.service.impl;

import com.agacostays.gateway.constants.GatewayConstants;
import com.agacostays.gateway.exception.GatewayUnauthorizedException;
import com.agacostays.gateway.exception.MissingAuthorizationHeaderException;
import com.agacostays.gateway.security.JwtClaims;
import com.agacostays.gateway.security.JwtTokenProvider;
import com.agacostays.gateway.service.GatewayAuthService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GatewayAuthServiceImpl implements GatewayAuthService {

    private final JwtTokenProvider tokenProvider;

    public GatewayAuthServiceImpl(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public Mono<JwtClaims> authenticate(String bearerToken) {
        if (bearerToken == null || bearerToken.isBlank()) {
            return Mono.error(new MissingAuthorizationHeaderException("Authorization header is required"));
        }

        if (!bearerToken.startsWith(GatewayConstants.BEARER_PREFIX)) {
            return Mono.error(new GatewayUnauthorizedException("Authorization header must use Bearer token"));
        }

        String token = bearerToken.substring(GatewayConstants.BEARER_PREFIX.length()).trim();
        if (token.isBlank()) {
            return Mono.error(new GatewayUnauthorizedException("Bearer token is empty"));
        }

        return tokenProvider.validate(token);
    }
}
