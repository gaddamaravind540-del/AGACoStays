package com.agacostays.room.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class AppConfig {

    @Bean
    public CommandLineRunner initDefaultRooms(JdbcTemplate jdbcTemplate) {
        return args -> {
            try {
                // Ensure default rooms exist for Branch 1
                jdbcTemplate.execute(
                        "INSERT INTO rooms (branch_id, room_number, room_type, base_price_per_day, current_price_per_day, description, floor, status, created_by, updated_by, created_at, updated_at) " +
                        "VALUES " +
                        "(1, 'B101', 'NORMAL', 1000.00, 1000.00, 'Standard room with double bed and attached washroom', 1, 'AVAILABLE', 1, 1, NOW(), NOW()), " +
                        "(1, 'B201', 'DELUXE', 1800.00, 1800.00, 'Deluxe room with AC, TV, WiFi and work desk', 2, 'AVAILABLE', 1, 1, NOW(), NOW()), " +
                        "(1, 'B301', 'LUXURY', 3000.00, 3000.00, 'Luxury suite with balcony view and mini bar', 3, 'AVAILABLE', 1, 1, NOW(), NOW()) " +
                        "ON DUPLICATE KEY UPDATE current_price_per_day = VALUES(current_price_per_day);"
                );
            } catch (Exception ignored) {
                // Ignore if tables are not yet generated or already populated
            }
        };
    }
}