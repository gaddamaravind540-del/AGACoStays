package com.agacostays.attendance.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class OpenApiConfig {
    @Bean OpenAPI attendanceOpenAPI() {
        return new OpenAPI().info(new Info().title("AGA CoStays Attendance Service API")
            .version("1.0.0").description("Staff attendance, corrections and reports"));
    }
}
