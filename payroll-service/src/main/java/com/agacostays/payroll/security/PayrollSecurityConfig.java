package com.agacostays.payroll.security;
import org.springframework.context.annotation.*; import org.springframework.security.config.annotation.web.builders.HttpSecurity; import org.springframework.security.config.http.SessionCreationPolicy; import org.springframework.security.web.*; import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration public class PayrollSecurityConfig{
 @Bean SecurityFilterChain securityFilterChain(HttpSecurity http,JwtAuthenticationFilter filter)throws Exception{return http.csrf(x->x.disable()).sessionManagement(x->x.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).authorizeHttpRequests(a->a
  .requestMatchers("/actuator/**","/swagger-ui/**","/swagger-ui.html","/v3/api-docs/**").permitAll()
  .requestMatchers("/api/staff/bank-account","/api/payroll/my-payroll","/api/payroll/*/salary-slip").hasAnyRole("STAFF","MANAGER","ROOT_ADMIN")
  .requestMatchers("/api/manager/**").hasAnyRole("MANAGER","ROOT_ADMIN")
  .anyRequest().authenticated()).addFilterBefore(filter,UsernamePasswordAuthenticationFilter.class).build();}
}
