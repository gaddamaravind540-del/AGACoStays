package com.agacostays.user.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestClient;

@Configuration
public class AppConfig {

    @Bean
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }

    @Bean
    public CommandLineRunner initUserRoles(JdbcTemplate jdbcTemplate) {
        return args -> {
            try {
                // Ensure default roles exist in user_db for registration and role mappings
                jdbcTemplate.execute(
                        "INSERT INTO roles (role_name, description, status) VALUES " +
                        "('ROOT_ADMIN', 'Platform owner with global access', 'ACTIVE'), " +
                        "('MANAGER', 'Business operations administrator', 'ACTIVE'), " +
                        "('CUSTOMER', 'Hotel customer', 'ACTIVE'), " +
                        "('RECEPTIONIST', 'Assigned-branch front desk role', 'ACTIVE'), " +
                        "('RESTAURANT_ADMIN', 'Assigned-branch restaurant administrator', 'ACTIVE'), " +
                        "('CHEF', 'Assigned-branch kitchen staff', 'ACTIVE'), " +
                        "('SERVING_STAFF', 'Assigned-branch food service staff', 'ACTIVE'), " +
                        "('HOUSEKEEPING_STAFF', 'Assigned-branch housekeeping staff', 'ACTIVE') " +
                        "ON DUPLICATE KEY UPDATE status = VALUES(status);"
                );
            } catch (Exception ignored) {
                // Ignore if tables are not initialized or if schema constraints differ
            }
        };
    }
}