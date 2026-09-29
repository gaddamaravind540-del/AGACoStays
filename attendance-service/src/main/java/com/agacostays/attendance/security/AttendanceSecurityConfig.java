package com.agacostays.attendance.security;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
@EnableMethodSecurity
public class AttendanceSecurityConfig {
    @Bean SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter filter)throws Exception{
        return http.csrf(x->x.disable())
            .sessionManagement(x->x.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(a->a
                .requestMatchers("/actuator/**","/swagger-ui/**","/swagger-ui.html","/v3/api-docs/**").permitAll()
                .requestMatchers("/api/attendance/**","/api/staff/**").authenticated()
                .requestMatchers("/api/manager/**").hasAnyRole("MANAGER","ROOT_ADMIN","RECEPTIONIST")
                .requestMatchers("/api/hotel-branches/**").hasAnyRole("MANAGER","ROOT_ADMIN","RECEPTIONIST")
                .anyRequest().authenticated())
            .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class).build();
    }
}
