package com.agacostays.restaurant.config;

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
    public CommandLineRunner initRestaurantData(JdbcTemplate jdbcTemplate) {
        return args -> {
            try {
                // Ensure default menu categories exist in restaurant_db
                jdbcTemplate.execute(
                        "INSERT INTO menu_categories (category_id, branch_id, category_name, description, status, created_at, updated_at) " +
                        "VALUES " +
                        "(1, 1, 'Starters', 'Appetizers and soups', 'ACTIVE', NOW(), NOW()), " +
                        "(2, 1, 'Main Course', 'Lunch and dinner main courses', 'ACTIVE', NOW(), NOW()), " +
                        "(3, 1, 'Beverages', 'Hot and cold beverages', 'ACTIVE', NOW(), NOW()), " +
                        "(4, 1, 'Desserts', 'Sweets and ice creams', 'ACTIVE', NOW(), NOW()) " +
                        "ON DUPLICATE KEY UPDATE category_name = VALUES(category_name);"
                );
            } catch (Exception ignored) {
                // Ignore if tables are not yet generated or schema differs
            }
        };
    }
}