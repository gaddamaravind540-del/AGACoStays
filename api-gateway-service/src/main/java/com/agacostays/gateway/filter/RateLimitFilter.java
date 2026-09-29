package com.agacostays.gateway.filter;

import com.agacostays.gateway.constants.GatewayConstants;
import com.agacostays.gateway.constants.HeaderConstants;
import com.agacostays.gateway.config.RateLimitConfig;
import com.agacostays.gateway.enums.RateLimitType;
import com.agacostays.gateway.exception.RateLimitExceededException;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RateLimiter;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class RateLimitFilter implements GlobalFilter, Ordered {

    private final RateLimiter<RedisRateLimiter.Config> rateLimiter;
    private final KeyResolver keyResolver;
    private final RateLimitConfig config;

    public RateLimitFilter(
            RedisRateLimiter rateLimiter,
            KeyResolver keyResolver,
            RateLimitConfig config) {
        this.rateLimiter = rateLimiter;
        this.keyResolver = keyResolver;
        this.config = config;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String routeId = exchange.getAttributeOrDefault(
                "org.springframework.cloud.gateway.support.ServerWebExchangeUtils.gatewayRouteId",
                "global");

        return keyResolver.resolve(exchange)
                .flatMap(key -> rateLimiter.isAllowed(routeId, key))
                .flatMap(response -> {
                    if (!response.isAllowed()) {
                        return Mono.error(new RateLimitExceededException("Gateway rate limit exceeded"));
                    }

                    if (response.getHeaders() != null) {
                        String remaining = response.getHeaders().get("X-RateLimit-Remaining");
                        if (remaining != null) {
                            exchange.getResponse().getHeaders().set(
                                    HeaderConstants.RATE_LIMIT_REMAINING, remaining);
                        }
                    }

                    exchange.getResponse().getHeaders().set(
                            HeaderConstants.RATE_LIMIT_REPLENISH,
                            String.valueOf(config.getReplenishRate()));
                    return chain.filter(exchange);
                })
                .onErrorResume(ex -> {
                    if (config.isFailOpen() && !(ex instanceof RateLimitExceededException)) {
                        return chain.filter(exchange);
                    }
                    return Mono.error(ex);
                });
    }

    @Override
    public int getOrder() {
        return -150;
    }
}
