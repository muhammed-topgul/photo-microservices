package com.mtopgul.photoapp.userservice.security;

import com.mtopgul.photoapp.tokenlib.TokenParser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
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
        SecurityContextHolder.getContext().setAuthentication(getAuthentication(authHeader, prefix));
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String authHeader, String prefix) {
        String token = authHeader.replace(prefix, "");
        String tokenSecret = environment.getProperty("token.secret");
        if (Objects.isNull(tokenSecret)) {
            return null;
        }
        TokenParser tokenParser = TokenParser.build(token, tokenSecret);
        String userId = tokenParser.getSubject();
        if (Objects.isNull(userId) || userId.isEmpty()) {
            return null;
        }
        return new UsernamePasswordAuthenticationToken(userId, null, tokenParser.getAuthorities());
    }

    private String getHeader(String name) {
        return environment.getProperty("auth.token.header.%s".formatted(name));
    }
}
