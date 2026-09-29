package com.agacostays.gateway.security;

import org.junit.jupiter.api.Test;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;

import static org.assertj.core.api.Assertions.assertThat;

class PublicRouteValidatorTest {

    private final PublicRouteValidator validator = new PublicRouteValidator();

    @Test
    void loginAndCatalogGetArePublic() {
        assertThat(validator.isPublic(MockServerHttpRequest.post("/api/auth/login").build())).isTrue();
        assertThat(validator.isPublic(MockServerHttpRequest.get("/api/cities").build())).isTrue();
        assertThat(validator.isPublic(MockServerHttpRequest.get("/api/hotel-branches/1").build())).isTrue();
    }

    @Test
    void logoutAndBookingCreationAreNotPublic() {
        assertThat(validator.isPublic(MockServerHttpRequest.post("/api/auth/logout").build())).isFalse();
        assertThat(validator.isPublic(MockServerHttpRequest.post("/api/hotel-branches/1/bookings").build())).isFalse();
    }
}
