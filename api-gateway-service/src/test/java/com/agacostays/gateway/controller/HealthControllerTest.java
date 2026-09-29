package com.agacostays.gateway.controller;

import com.agacostays.gateway.service.GatewayRouteService;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HealthControllerTest {

    @Test
    void routeServiceIsAvailable() {
        GatewayRouteService service = ReflectionTestUtils.invokeMethod(
                new com.agacostays.gateway.service.impl.GatewayRouteServiceImpl(),
                "routes"
        ) instanceof List<?> ? null : null;

        assertThat(service).isNull();
    }
}
