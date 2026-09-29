package com.agacostays.payment.security;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class PaymentSecurityConfig {
 @Bean SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwt, AuthenticationEntryPointHandler entry, AccessDeniedHandlerImpl denied) throws Exception {
  http.csrf(csrf->csrf.disable()).sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .exceptionHandling(e->e.authenticationEntryPoint(entry).accessDeniedHandler(denied))
      .authorizeHttpRequests(a->a.requestMatchers("/actuator/health","/api/payments/webhook","/api/payments/webhooks/razorpay","/swagger-ui.html","/swagger-ui/**","/v3/api-docs/**").permitAll().anyRequest().authenticated())
      .addFilterBefore(jwt, UsernamePasswordAuthenticationFilter.class);
  return http.build();
 }
}
