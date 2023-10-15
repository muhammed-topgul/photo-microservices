package com.mtopgul.photoapp.userservice.security;

import com.mtopgul.photoapp.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author muhammed-topgul
 * @since 10/10/2023 09:11
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurity {
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Environment environment;

    @Bean
    protected SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManager authenticationManager = getAuthenticationManager(httpSecurity);
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        httpSecurity.authorizeHttpRequests(requests ->
                        requests.requestMatchers(HttpMethod.POST, "/api/users/**")
                                .access(authApiGatewayRequests())
                                .requestMatchers(HttpMethod.GET, "**/status/check").permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/error/**")).permitAll())
                .addFilter(getAuthenticationFilter(authenticationManager))
                .authenticationManager(authenticationManager)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return httpSecurity.build();
    }

    private AuthenticationFilter getAuthenticationFilter(AuthenticationManager authenticationManager) {
        AuthenticationFilter filter = new AuthenticationFilter(authenticationManager, userService, environment);
        filter.setFilterProcessesUrl("/api/users/sign-in");
        return filter;
    }

    private AuthenticationManager getAuthenticationManager(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity
                .getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userService)
                .passwordEncoder(bCryptPasswordEncoder);
        return authenticationManagerBuilder.build();
    }

    private WebExpressionAuthorizationManager authApiGatewayRequests() {
        try {
            return new WebExpressionAuthorizationManager("hasIpAddress('%s')".formatted(InetAddress.getLocalHost().getHostAddress()));
        } catch (UnknownHostException e) {
            throw new RuntimeException("Cannot find IP address.");
        }
    }
}
