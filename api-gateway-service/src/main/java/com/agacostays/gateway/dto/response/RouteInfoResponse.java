package com.agacostays.gateway.dto.response;

import com.agacostays.gateway.enums.GatewayRouteType;

public record RouteInfoResponse(
        String id,
        String path,
        String downstreamService,
        GatewayRouteType routeType,
        String requiredRoles,
        String description
) {}
