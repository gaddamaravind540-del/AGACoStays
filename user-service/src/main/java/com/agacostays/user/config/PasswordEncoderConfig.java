package com.agacostays.user.config;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration
public class PasswordEncoderConfig {
 @Bean PasswordEncoder passwordEncoder(){return new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();}
}
