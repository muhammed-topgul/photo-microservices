package com.mtopgul.photoapp.userservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mtopgul.photoapp.userservice.dto.LoginDto;
import com.mtopgul.photoapp.userservice.dto.UserDto;
import com.mtopgul.photoapp.userservice.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;

/**
 * @author muhammed-topgul
 * @since 15/10/2023 19:05
 */
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final UserService userService;
    private final Environment environment;

    public AuthenticationFilter(AuthenticationManager authenticationManager, UserService userService, Environment environment) {
        super(authenticationManager);
        this.userService = userService;
        this.environment = environment;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            var loginDto = new ObjectMapper().readValue(request.getInputStream(), LoginDto.class);
            var token = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword(), new ArrayList<>());
            return getAuthenticationManager().authenticate(token);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) {
        UserDto userDto = userService.findByEmail(((User) authResult.getPrincipal()).getUsername());
        Instant now = Instant.now();
        String token = Jwts.builder()
                .claim("scope", authResult.getAuthorities())
                .setSubject(userDto.getId())
                .setExpiration(Date.from(now.plusMillis(getExpireDate())))
                .setIssuedAt(Date.from(now))
                .signWith(getSecretKey(), SignatureAlgorithm.HS512)
                .compact();
        response.addHeader("token", token);
        response.addHeader("userId", userDto.getId());
    }

    private long getExpireDate() {
        return Long.parseLong(Objects
                .requireNonNullElseGet(environment.getProperty("token.expiration_time"), () -> "360000"));
    }

    private SecretKey getSecretKey() {
        byte[] secretKeyBytes = Base64.getEncoder()
                        .encode(Objects.requireNonNull(environment.getProperty("token.secret"))
                        .getBytes());
        return new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS512.getJcaName());
    }
}
