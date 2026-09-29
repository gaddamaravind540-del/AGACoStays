package com.agacostays.gateway.service;

import com.agacostays.gateway.dto.response.RouteInfoResponse;
import com.agacostays.gateway.service.impl.GatewayRouteServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GatewayRouteServiceImplTest {

    @Test
    void shouldExposeRequiredGatewayRouteFamilies() {
        List<RouteInfoResponse> routes = new GatewayRouteServiceImpl().routes();

        assertThat(routes).extracting(RouteInfoResponse::path)
                .contains(
                        "/api/auth/**",
                        "/api/cities/**",
                        "/api/hotel-branches/**",
                        "/api/root-admin/**",
                        "/api/manager/**",
                        "/api/staff/**",
                        "/api/bookings/**",
                        "/api/payments/**",
                        "/api/billing/**",
                        "/api/restaurant/**",
                        "/api/attendance/**",
                        "/api/payroll/**",
                        "/api/analytics/**",
                        "/api/support/**"
                );
    }
}
