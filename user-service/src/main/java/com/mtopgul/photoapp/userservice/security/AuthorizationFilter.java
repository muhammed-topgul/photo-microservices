package com.mtopgul.photoapp.userservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.core.env.Environment;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Objects;

/**
 * @author muhammed-topgul
 * @since 31/10/2023 17:34
 */
public class AuthorizationFilter extends BasicAuthenticationFilter {
    private final Environment environment;

    public AuthorizationFilter(AuthenticationManager authenticationManager, Environment environment) {
        super(authenticationManager);
        this.environment = environment;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String headerName = getHeader("name");
        String authHeader = request.getHeader(headerName);
        String prefix = getHeader("prefix");
        if (Objects.isNull(authHeader) || !authHeader.startsWith(prefix)) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(authHeader, prefix);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String authHeader, String prefix) {
        String token = authHeader.replace(prefix, "");
        String tokenSecret = environment.getProperty("token.secret");
        if (Objects.isNull(tokenSecret)) {
            return null;
        }
        byte[] secretKeyBytes = Base64.getEncoder().encode(tokenSecret.getBytes());
        SecretKey secretKey = new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS512.getJcaName());
        String userId = ((Claims) Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parse(token)
                .getBody())
                .getSubject();
        if (Objects.isNull(userId) || userId.isEmpty()) {
            return null;
        }
        return new UsernamePasswordAuthenticationToken(userId, null, new ArrayList<>());
    }

    private String getHeader(String name) {
        return environment.getProperty("auth.token.header.%s".formatted(name));
    }
}
