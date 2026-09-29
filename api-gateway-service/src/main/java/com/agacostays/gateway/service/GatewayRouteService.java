package com.agacostays.gateway.service;

import com.agacostays.gateway.dto.response.RouteInfoResponse;

import java.util.List;

public interface GatewayRouteService {
    List<RouteInfoResponse> routes();
}
