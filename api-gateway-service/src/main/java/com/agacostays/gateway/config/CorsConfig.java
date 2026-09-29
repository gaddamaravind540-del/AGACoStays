package com.agacostays.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    CorsWebFilter corsWebFilter(
            @Value("${gateway.cors.allowed-origins:http://localhost:3000}") String allowedOrigins,
            @Value("${gateway.cors.allowed-methods:GET,POST,PUT,PATCH,DELETE,OPTIONS}") String allowedMethods,
            @Value("${gateway.cors.allowed-headers:*}") String allowedHeaders,
            @Value("${gateway.cors.allow-credentials:true}") boolean allowCredentials) {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.stream(allowedOrigins.split(","))
                .map(String::trim).filter(s -> !s.isBlank()).toList());
        configuration.setAllowedMethods(Arrays.stream(allowedMethods.split(","))
                .map(String::trim).filter(s -> !s.isBlank()).toList());
        configuration.setAllowedHeaders(allowedHeaders.equals("*")
                ? List.of("*")
                : Arrays.stream(allowedHeaders.split(",")).map(String::trim).toList());
        configuration.setAllowCredentials(allowCredentials);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return new CorsWebFilter(source);
    }
}
