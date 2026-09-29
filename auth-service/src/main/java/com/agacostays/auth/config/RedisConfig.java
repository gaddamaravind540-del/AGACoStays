package com.agacostays.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {
    // Redis is available for OTP/token caching and future session controls.
    // Persistent token state remains in auth_db.
}
