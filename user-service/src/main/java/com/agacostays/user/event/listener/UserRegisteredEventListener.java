package com.agacostays.user.event.listener;

import com.agacostays.user.entity.Customer;
import com.agacostays.user.entity.Role;
import com.agacostays.user.entity.User;
import com.agacostays.user.enums.CustomerStatus;
import com.agacostays.user.enums.UserStatus;
import com.agacostays.user.enums.UserType;
import com.agacostays.user.repository.CustomerRepository;
import com.agacostays.user.repository.RoleRepository;
import com.agacostays.user.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserRegisteredEventListener {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CustomerRepository customerRepository;
    private final ObjectMapper objectMapper;

    public UserRegisteredEventListener(
            UserRepository userRepository,
            RoleRepository roleRepository,
            CustomerRepository customerRepository,
            ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.customerRepository = customerRepository;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(
            topics = "${app.kafka.topics.user-registered:auth.user-registered}",
            groupId = "${spring.kafka.consumer.group-id:agacostays-user-service}")
    @Transactional
    public void onUserRegistered(String payload) {
        try {
            JsonNode event = objectMapper.readTree(payload);
            String roleName = event.path("roleName").asText("CUSTOMER");
            if (!"CUSTOMER".equalsIgnoreCase(roleName)) {
                return;
            }

            long userId = event.path("userId").asLong();
            String email = event.path("email").asText();
            String fullName = event.path("fullName").asText();

            if (userRepository.existsById(userId) || userRepository.existsByEmailIgnoreCase(email)) {
                return;
            }

            Role customerRole = roleRepository.findByRoleNameIgnoreCase("CUSTOMER").orElse(null);
            if (customerRole == null) {
                return;
            }

            User user = userRepository.save(User.builder()
                    .userId(userId)
                    .fullName(fullName)
                    .email(email.toLowerCase())
                    .role(customerRole)
                    .userType(UserType.CUSTOMER)
                    .status(UserStatus.ACTIVE)
                    .build());

            customerRepository.save(Customer.builder()
                    .user(user)
                    .status(CustomerStatus.ACTIVE)
                    .build());

        } catch (Exception ignored) {
            // Keep consumer idempotent and avoid breaking the Kafka listener thread.
        }
    }
}
