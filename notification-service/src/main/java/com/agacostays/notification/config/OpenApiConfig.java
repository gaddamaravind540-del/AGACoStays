package com.agacostays.notification.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    OpenAPI notificationOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("AGA CoStays Notification Service API")
                .version("1.0.0")
                .description("Email, SMS, notification logs, templates and checkout reminders"));
    }
}
