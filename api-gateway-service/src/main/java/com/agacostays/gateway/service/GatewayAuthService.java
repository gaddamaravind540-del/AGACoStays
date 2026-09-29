package com.agacostays.gateway.service;

import com.agacostays.gateway.security.JwtClaims;
import reactor.core.publisher.Mono;

public interface GatewayAuthService {
    Mono<JwtClaims> authenticate(String bearerToken);
}
