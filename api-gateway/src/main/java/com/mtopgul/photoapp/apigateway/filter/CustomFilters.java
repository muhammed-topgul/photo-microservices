package com.mtopgul.photoapp.apigateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

/**
 * @author muhammed-topgul
 * @since 17/10/2023 12:00
 */
@Configuration
@Slf4j
public class CustomFilters {
    @Bean
    public GlobalFilter secondFilters() {
        return ((exchange, chain) -> {
            log.info("Second Pre-filter executing...");
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                log.info("Second Post-filter executed...");
            }));
        });
    }

    @Bean
    public GlobalFilter thirdFilters() {
        return ((exchange, chain) -> {
            log.info("Third Pre-filter executing...");
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                log.info("Third Post-filter executed...");
            }));
        });
    }
}
