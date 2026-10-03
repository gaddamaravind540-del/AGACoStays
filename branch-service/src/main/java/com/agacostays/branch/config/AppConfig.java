package com.agacostays.branch.config;

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
    public CommandLineRunner initBranchData(JdbcTemplate jdbcTemplate) {
        return args -> {
            try {
                // Ensure at least one city exists
                jdbcTemplate.execute(
                        "INSERT INTO cities (city_id, city_name, state, country, status, created_at, updated_at) " +
                        "VALUES (1, 'Bengaluru', 'Karnataka', 'India', 'ACTIVE', NOW(), NOW()) " +
                        "ON DUPLICATE KEY UPDATE city_name = VALUES(city_name);"
                );

                // Ensure Branch 1 exists for downstream services (room-service, booking-service)
                jdbcTemplate.execute(
                        "INSERT INTO hotel_branches (branch_id, city_id, branch_name, branch_code, address, status, created_at, updated_at) " +
                        "VALUES (1, 1, 'AGA CoStays Central', 'AGA-BLR-001', '100 MG Road, Bengaluru', 'ACTIVE', NOW(), NOW()) " +
                        "ON DUPLICATE KEY UPDATE branch_name = VALUES(branch_name);"
                );
            } catch (Exception ignored) {
                // Ignore if tables are created with slightly different column naming or during schema transition
            }
        };
    }
}