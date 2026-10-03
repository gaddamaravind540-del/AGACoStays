package com.agacostays.auth.config;

import com.agacostays.auth.entity.Role;
import com.agacostays.auth.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class AppConfig {

    @Bean
    CommandLineRunner initDefaultRoles(RoleRepository roleRepository) {
        return args -> {
            List<String> defaultRoles = List.of(
                    "ROOT_ADMIN",
                    "MANAGER",
                    "CUSTOMER",
                    "RECEPTIONIST",
                    "RESTAURANT_ADMIN",
                    "CHEF",
                    "SERVING_STAFF",
                    "HOUSEKEEPING_STAFF"
            );

            for (String roleName : defaultRoles) {
                if (roleRepository.findByRoleNameIgnoreCase(roleName).isEmpty()) {
                    Role role = Role.builder()
                            .roleName(roleName)
                            .description(roleName + " default role")
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .build();
                    roleRepository.save(role);
                }
            }
        };
    }
}