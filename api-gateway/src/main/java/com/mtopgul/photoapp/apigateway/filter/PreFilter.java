package com.mtopgul.photoapp.apigateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Set;

/**
 * @author muhammed-topgul
 * @since 17/10/2023 11:38
 */
@Component
@Slf4j
public class PreFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("Pre-filter executing...");
        String path = exchange.getRequest().getPath().toString();
        log.info("> Path: {}", path);
        HttpHeaders headers = exchange.getRequest().getHeaders();
        Set<String> headerNames = headers.keySet();
        headerNames.forEach(headerName -> {
            String headerValue = headers.getFirst(headerName);
            log.info("> {}: {}", headerName, headerValue);
        });
        return chain.filter(exchange);
    }
}
