package com.agacostays.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class RedisRateLimiterConfig {

    @Bean
    RedisRateLimiter redisRateLimiter(RateLimitConfig config) {
        return new RedisRateLimiter(config.getReplenishRate(), config.getBurstCapacity());
    }

    @Bean
    KeyResolver gatewayKeyResolver() {
        return exchange -> {
            String userId = exchange.getRequest().getHeaders().getFirst("X-User-Id");
            if (userId != null && !userId.isBlank()) {
                return Mono.just("user:" + userId);
            }

            String forwardedFor = exchange.getRequest().getHeaders().getFirst("X-Forwarded-For");
            if (forwardedFor != null && !forwardedFor.isBlank()) {
                return Mono.just("ip:" + forwardedFor.split(",")[0].trim());
            }

            String host = exchange.getRequest().getRemoteAddress() == null
                    ? "unknown"
                    : String.valueOf(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());

            return Mono.just("ip:" + host);
        };
    }
}
