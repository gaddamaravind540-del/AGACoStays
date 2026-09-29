package com.agacostays.auth.service;

import com.agacostays.auth.entity.Role;
import com.agacostays.auth.entity.User;
import com.agacostays.auth.enums.AuthProvider;
import com.agacostays.auth.enums.UserStatus;
import com.agacostays.auth.enums.UserType;
import com.agacostays.auth.service.impl.JwtServiceImpl;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JwtServiceImplTest {

    @Test
    void generatesAndParsesAccessToken() {
        String secret = "c2VjdXJlLWFnYS1jb3N0YXlzLWF1dGgtc2VjcmV0LWtleS1mb3ItcHJvamVjdC0yMDI2";
        JwtService jwtService = new JwtServiceImpl(secret, 900);

        User user = User.builder()
                .userId(100L)
                .fullName("Test Customer")
                .email("test@example.com")
                .role(Role.builder().roleName("CUSTOMER").build())
                .userType(UserType.CUSTOMER)
                .status(UserStatus.ACTIVE)
                .authProvider(AuthProvider.LOCAL)
                .build();

        String token = jwtService.generateAccessToken(user);
        var claims = jwtService.parseAndValidate(token);

        assertThat(claims.userId()).isEqualTo(100L);
        assertThat(claims.email()).isEqualTo("test@example.com");
        assertThat(claims.role()).isEqualTo("CUSTOMER");
    }
}
