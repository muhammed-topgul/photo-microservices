package com.mtopgul.photoapp.apigateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

/**
 * @author muhammed-topgul
 * @since 16/10/2023 00:37
 */
@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {
    @Getter
    public static class Config {
        private List<String> authorities;

        public void setAuthorities(String authorities) {
            this.authorities = List.of(authorities.split(" "));
        }
    }

    private final Environment environment;

    public AuthorizationHeaderFilter(Environment environment) {
        super(Config.class);
        this.environment = environment;
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return List.of("authorities");
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "Request header must has %s. key!".formatted(HttpHeaders.AUTHORIZATION));
            }
            String jwt = Objects
                    .requireNonNull(request.getHeaders().get(HttpHeaders.AUTHORIZATION))
                    .get(0)
                    .replace("Bearer", "");
            boolean nonRequiredAuthority = getAuthorities(jwt)
                    .stream()
                    .noneMatch(authority -> config.getAuthorities().contains(authority));
            if (nonRequiredAuthority) {
                return onError(exchange, "You are not authorized for this operation!");
            }
            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String message) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.writeWith(Mono.just(response.bufferFactory().wrap(message.getBytes())));
    }

    private List<String> getAuthorities(String jwt) {
        List<String> authorities = new ArrayList<>();
        List<Map<String, String>> scopes = ((Claims) Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parse(jwt)
                .getBody())
                .get("scope", List.class);

        scopes.forEach(scopeMap -> authorities.add(scopeMap.get("authority")));
        return authorities;
    }

    private boolean isValidJwt(String jwt) {
        try {
            String subject = ((Claims) Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parse(jwt)
                    .getBody())
                    .getSubject();
            if (Objects.isNull(subject) || subject.isEmpty()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private SecretKey getSecretKey() {
        byte[] secretKeyBytes = Base64.getEncoder()
                .encode(Objects.requireNonNull(environment.getProperty("token.secret"))
                        .getBytes());
        return new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS512.getJcaName());
    }
}
