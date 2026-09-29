package com.agacostays.gateway.exception;

public class GatewayUnauthorizedException extends RuntimeException {
    public GatewayUnauthorizedException(String message) {
        super(message);
    }
}
