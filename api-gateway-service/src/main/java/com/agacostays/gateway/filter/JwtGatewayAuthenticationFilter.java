package com.agacostays.gateway.filter;

import com.agacostays.gateway.constants.GatewayConstants;
import com.agacostays.gateway.constants.HeaderConstants;
import com.agacostays.gateway.exception.GatewayAccessDeniedException;
import com.agacostays.gateway.security.PublicRouteValidator;
import com.agacostays.gateway.security.RoleRouteValidator;
import com.agacostays.gateway.service.GatewayAuthService;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import reactor.core.publisher.Mono;

@Component
public class JwtGatewayAuthenticationFilter implements GlobalFilter, Ordered {

    private final PublicRouteValidator publicRouteValidator;
    private final RoleRouteValidator roleRouteValidator;
    private final GatewayAuthService gatewayAuthService;

    public JwtGatewayAuthenticationFilter(
            PublicRouteValidator publicRouteValidator,
            RoleRouteValidator roleRouteValidator,
            GatewayAuthService gatewayAuthService) {
        this.publicRouteValidator = publicRouteValidator;
        this.roleRouteValidator = roleRouteValidator;
        this.gatewayAuthService = gatewayAuthService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getPath().value();

        if (publicRouteValidator.isPublic(exchange.getRequest())) {
            return chain.filter(exchange);
        }

        String authorization = exchange.getRequest().getHeaders().getFirst(GatewayConstants.AUTHORIZATION_HEADER);

        return gatewayAuthService.authenticate(authorization)
                .flatMap(claims -> {
                    if (!roleRouteValidator.isAllowed(path, claims.roles())) {
                        return Mono.error(new GatewayAccessDeniedException(
                                "Role does not have access to " + path));
                    }

                    ServerHttpRequest.Builder builder = exchange.getRequest().mutate();
                    builder.headers(headers -> {
                        headers.remove(GatewayConstants.USER_ID_HEADER);
                        headers.remove(GatewayConstants.USERNAME_HEADER);
                        headers.remove(GatewayConstants.ROLES_HEADER);
                        headers.remove(GatewayConstants.BRANCH_ID_HEADER);
                        headers.remove(HeaderConstants.INTERNAL_AUTHENTICATED);

                        if (claims.userId() != null) headers.set(GatewayConstants.USER_ID_HEADER, claims.userId());
                        if (claims.username() != null) headers.set(GatewayConstants.USERNAME_HEADER, claims.username());
                        headers.set(GatewayConstants.ROLES_HEADER, String.join(",", claims.roles()));
                        if (claims.branchId() != null) headers.set(GatewayConstants.BRANCH_ID_HEADER, claims.branchId());
                        headers.set(HeaderConstants.INTERNAL_AUTHENTICATED, "true");
                    });

                    return chain.filter(exchange.mutate().request(builder.build()).build());
                });
    }

    @Override
    public int getOrder() {
        return -200;
    }
}
