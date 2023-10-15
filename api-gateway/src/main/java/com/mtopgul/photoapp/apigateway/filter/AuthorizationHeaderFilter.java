package com.mtopgul.photoapp.apigateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

/**
 * @author muhammed-topgul
 * @since 16/10/2023 00:37
 */
@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {
    @Override
    public GatewayFilter apply(Config config) {
        return null;
    }

    public static class Config {
    }
}
