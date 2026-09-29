package com.agacostays.analytics.security;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class AnalyticsSecurityConfig {
    @Bean SecurityFilterChain securityFilterChain(HttpSecurity http,JwtAuthenticationFilter filter)throws Exception{
        return http.csrf(c->c.disable())
            .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(a->a
                .requestMatchers("/actuator/**","/swagger-ui/**","/swagger-ui.html","/v3/api-docs/**").permitAll()
                .requestMatchers("/api/analytics/hotel-dashboard","/api/analytics/restaurant-dashboard",
                                 "/api/analytics/hotel-branches/**","/api/analytics/manager-dashboard",
                                 "/api/analytics/root-admin-dashboard","/api/analytics/revenue",
                                 "/api/analytics/occupancy","/api/analytics/attendance","/api/analytics/payroll",
                                 "/api/analytics/reports/**").hasAnyRole("MANAGER","ROOT_ADMIN","RESTAURANT_ADMIN","RECEPTIONIST")
                .requestMatchers("/api/manager/**").hasAnyRole("MANAGER","ROOT_ADMIN")
                .requestMatchers("/api/root-admin/**").hasRole("ROOT_ADMIN")
                .requestMatchers("/api/hotel-branches/**").hasAnyRole("MANAGER","RECEPTIONIST","RESTAURANT_ADMIN")
                .anyRequest().authenticated())
            .addFilterBefore(filter,UsernamePasswordAuthenticationFilter.class).build();
    }
}
