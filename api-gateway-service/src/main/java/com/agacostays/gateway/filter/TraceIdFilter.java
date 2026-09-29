package com.agacostays.gateway.filter;

import com.agacostays.gateway.constants.GatewayConstants;
import com.agacostays.gateway.util.TraceIdUtil;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import reactor.core.publisher.Mono;

@Component
public class TraceIdFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String traceId = TraceIdUtil.resolveTraceId(exchange.getRequest());

        ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                .header(GatewayConstants.DEFAULT_TRACE_ID_HEADER, traceId)
                .header(GatewayConstants.CORRELATION_ID_HEADER, traceId)
                .build();

        exchange.getResponse().getHeaders().set(GatewayConstants.DEFAULT_TRACE_ID_HEADER, traceId);
        return chain.filter(exchange.mutate().request(mutatedRequest).build());
    }

    @Override
    public int getOrder() {
        return -300;
    }
}
