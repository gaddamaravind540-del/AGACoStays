package com.agacostays.booking.config;

import io.swagger.v3.oas.models.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI bookingOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("AGA CoStays Booking Service API")
                        .version("1.0")
                        .description("Room reservation and booking lifecycle APIs"));
    }
}
