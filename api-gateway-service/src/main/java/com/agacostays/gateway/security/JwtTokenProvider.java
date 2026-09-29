package com.agacostays.gateway.security;

import com.agacostays.gateway.exception.ExpiredJwtTokenException;
import com.agacostays.gateway.exception.InvalidJwtTokenException;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class JwtTokenProvider {

    private final ReactiveJwtDecoder jwtDecoder;

    public JwtTokenProvider(ReactiveJwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    public Mono<JwtClaims> validate(String token) {
        return jwtDecoder.decode(token)
                .map(JwtClaims::fromJwt)
                .onErrorMap(JwtValidationException.class, ex -> {
                    String message = ex.getMessage() == null ? "" : ex.getMessage().toLowerCase();
                    if (message.contains("expired")) {
                        return new ExpiredJwtTokenException("JWT token has expired");
                    }
                    return new InvalidJwtTokenException("JWT token is invalid");
                })
                .onErrorMap(ex -> {
                    if (ex instanceof InvalidJwtTokenException || ex instanceof ExpiredJwtTokenException) {
                        return ex;
                    }
                    return new InvalidJwtTokenException("JWT token is invalid");
                });
    }
}
