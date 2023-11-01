package com.mtopgul.photoapp.userservice.service;

import com.mtopgul.photoapp.userservice.dto.AlbumDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author muhammed-topgul
 * @since 18/10/2023 16:40
 */
@FeignClient(name = "album-service")
public interface AlbumServiceClient {
    Logger log = Logger.getLogger("AlbumServiceClient");

    @GetMapping("/api/albums/{id}")
    @Retry(name = "album-service")
    @CircuitBreaker(name = "album-service", fallbackMethod = "getAlbumsFallback")
    List<AlbumDto> getAlbums(@PathVariable(name = "id") String id,
                             @RequestHeader("Authorization") String authorization);

    default List<AlbumDto> getAlbumsFallback(String id, String authorization, Throwable exception) {
        log.info("Param " + id);
        log.info("Exception took place: " + exception.getMessage());
        return new ArrayList<>();
    }
}
