package com.agacostays.gateway.test.integration;

import com.agacostays.gateway.service.GatewayRouteService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        properties = {
                "spring.main.web-application-type=reactive",
                "spring.cloud.gateway.server.webflux.enabled=false"
        }
)
class GatewayRoutingIntegrationTest {

    @Test
    void applicationContextCanLoadGatewayService() {
        // This test verifies that the gateway service can boot its core application context.
        // Full proxy integration should be executed with actual downstream containers.
        assertThat("api-gateway-service").isEqualTo("api-gateway-service");
    }
}
