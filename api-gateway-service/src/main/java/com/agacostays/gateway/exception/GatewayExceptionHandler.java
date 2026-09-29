package com.agacostays.gateway.exception;

import com.agacostays.gateway.dto.response.GatewayErrorResponse;
import com.agacostays.gateway.enums.GatewayErrorCode;
import com.agacostays.gateway.util.TraceIdUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Component
@Order(-2)
public class GatewayExceptionHandler implements ErrorWebExceptionHandler {

    private final ObjectMapper objectMapper;

    public GatewayExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable throwable) {
        if (exchange.getResponse().isCommitted()) {
            return Mono.error(throwable);
        }

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        GatewayErrorCode code = GatewayErrorCode.INTERNAL_GATEWAY_ERROR;
        String message = "Unexpected gateway error";

        if (throwable instanceof MissingAuthorizationHeaderException) {
            status = HttpStatus.UNAUTHORIZED;
            code = GatewayErrorCode.MISSING_AUTHORIZATION;
            message = throwable.getMessage();
        } else if (throwable instanceof ExpiredJwtTokenException) {
            status = HttpStatus.UNAUTHORIZED;
            code = GatewayErrorCode.EXPIRED_JWT;
            message = throwable.getMessage();
        } else if (throwable instanceof InvalidJwtTokenException || throwable instanceof GatewayUnauthorizedException) {
            status = HttpStatus.UNAUTHORIZED;
            code = GatewayErrorCode.INVALID_JWT;
            message = throwable.getMessage();
        } else if (throwable instanceof GatewayAccessDeniedException) {
            status = HttpStatus.FORBIDDEN;
            code = GatewayErrorCode.ACCESS_DENIED;
            message = throwable.getMessage();
        } else if (throwable instanceof GatewayRouteException) {
            status = HttpStatus.BAD_GATEWAY;
            code = GatewayErrorCode.ROUTE_ERROR;
            message = throwable.getMessage();
        } else if (throwable instanceof DownstreamServiceUnavailableException) {
            status = HttpStatus.SERVICE_UNAVAILABLE;
            code = GatewayErrorCode.DOWNSTREAM_SERVICE_UNAVAILABLE;
            message = throwable.getMessage();
        } else if (throwable instanceof RateLimitExceededException) {
            status = HttpStatus.TOO_MANY_REQUESTS;
            code = GatewayErrorCode.RATE_LIMIT_EXCEEDED;
            message = throwable.getMessage();
        }

        String traceId = TraceIdUtil.resolveTraceId(exchange.getRequest());

        GatewayErrorResponse body = new GatewayErrorResponse(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                code,
                exchange.getRequest().getPath().value(),
                traceId
        );

        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        try {
            byte[] bytes = objectMapper.writeValueAsBytes(body);
            return response.writeWith(Mono.just(response.bufferFactory().wrap(bytes)));
        } catch (Exception serializationError) {
            return response.setComplete();
        }
    }
}
