package com.agacostays.gateway.filter;

import com.agacostays.gateway.constants.GatewayConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import reactor.core.publisher.Mono;

@Component
public class RequestLoggingFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        long start = System.currentTimeMillis();
        return chain.filter(exchange)
                .doFinally(signal -> {
                    long elapsed = System.currentTimeMillis() - start;
                    String traceId = exchange.getRequest().getHeaders().getFirst(GatewayConstants.DEFAULT_TRACE_ID_HEADER);
                    log.info("gateway_request method={} path={} status={} traceId={} durationMs={}",
                            exchange.getRequest().getMethod(),
                            exchange.getRequest().getPath().value(),
                            exchange.getResponse().getStatusCode(),
                            traceId,
                            elapsed);
                });
    }

    @Override
    public int getOrder() {
        return -250;
    }
}
