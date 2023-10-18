package com.mtopgul.photoapp.userservice.service;

import com.mtopgul.photoapp.userservice.dto.AlbumDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author muhammed-topgul
 * @since 18/10/2023 16:40
 */
@FeignClient(name = "album-service")
public interface AlbumServiceClient {
    @GetMapping("/api/albums/{id}")
    List<AlbumDto> getAlbums(@PathVariable String id);
}
