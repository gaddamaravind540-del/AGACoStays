package com.agacostays.gateway.controller;

import com.agacostays.gateway.constants.GatewayConstants;
import com.agacostays.gateway.dto.response.ApiResponse;
import com.agacostays.gateway.dto.response.GatewayHealthResponse;
import com.agacostays.gateway.dto.response.RouteInfoResponse;
import com.agacostays.gateway.service.GatewayRouteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/gateway")
public class HealthController {

    private final GatewayRouteService gatewayRouteService;

    public HealthController(GatewayRouteService gatewayRouteService) {
        this.gatewayRouteService = gatewayRouteService;
    }

    @GetMapping("/health")
    public ResponseEntity<ApiResponse<GatewayHealthResponse>> health(ServerWebExchange exchange) {
        String traceId = resolveTraceId(exchange);
        GatewayHealthResponse health = new GatewayHealthResponse(
                "api-gateway-service",
                "UP",
                Instant.now()
        );
        return ResponseEntity.ok(ApiResponse.success("Gateway is running", health, traceId));
    }

    @GetMapping("/routes")
    public ResponseEntity<ApiResponse<List<RouteInfoResponse>>> routes(ServerWebExchange exchange) {
        String traceId = resolveTraceId(exchange);
        return ResponseEntity.ok(ApiResponse.success(
                "Gateway route catalog",
                gatewayRouteService.routes(),
                traceId
        ));
    }

    private String resolveTraceId(ServerWebExchange exchange) {
        String traceId = exchange.getRequest().getHeaders().getFirst(GatewayConstants.DEFAULT_TRACE_ID_HEADER);
        return traceId == null || traceId.isBlank() ? "unknown" : traceId;
    }
}
