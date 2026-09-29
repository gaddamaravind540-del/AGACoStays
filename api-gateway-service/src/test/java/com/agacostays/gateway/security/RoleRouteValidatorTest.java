package com.agacostays.gateway.security;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RoleRouteValidatorTest {

    private final RoleRouteValidator validator = new RoleRouteValidator();

    @Test
    void rootAdminRouteRequiresRootAdmin() {
        assertThat(validator.isAllowed("/api/root-admin/managers", List.of("ROOT_ADMIN"))).isTrue();
        assertThat(validator.isAllowed("/api/root-admin/managers", List.of("MANAGER"))).isFalse();
    }

    @Test
    void managerRouteAcceptsManagerAndRootAdmin() {
        assertThat(validator.isAllowed("/api/manager/payroll", List.of("MANAGER"))).isTrue();
        assertThat(validator.isAllowed("/api/manager/payroll", List.of("ROOT_ADMIN"))).isTrue();
        assertThat(validator.isAllowed("/api/manager/payroll", List.of("CUSTOMER"))).isFalse();
    }

    @Test
    void customerCanAccessBookingFamilyAtGateway() {
        assertThat(validator.isAllowed("/api/bookings/my-bookings", List.of("CUSTOMER"))).isTrue();
    }
}
