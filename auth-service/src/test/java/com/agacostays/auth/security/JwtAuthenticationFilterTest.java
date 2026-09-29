package com.agacostays.auth.security;

import com.agacostays.auth.service.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class JwtAuthenticationFilterTest {

    @AfterEach
    void clearContext() {
        org.springframework.security.core.context.SecurityContextHolder.clearContext();
    }

    @Test
    void claimsObjectCarriesIdentity() {
        JwtClaims claims = new JwtClaims(
                12L,
                "customer@example.com",
                "Customer",
                "CUSTOMER",
                "CUSTOMER",
                Instant.now().plusSeconds(300)
        );

        assertThat(claims.userId()).isEqualTo(12L);
        assertThat(claims.role()).isEqualTo("CUSTOMER");
    }
}
