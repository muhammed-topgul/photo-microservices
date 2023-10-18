package com.mtopgul.photoapp.albumservice.controller;

import com.mtopgul.photoapp.albumservice.dto.AlbumDto;
import com.mtopgul.photoapp.albumservice.service.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author muhammed-topgul
 * @since 06/10/2023 10:07
 */
@RestController
@RequestMapping("/api/albums")
@RequiredArgsConstructor
public class AlbumController {
    private final AlbumService albumService;

    @GetMapping("/status/check")
    public String status() {
        return "Album Service is up and running!";
    }

    @GetMapping("/{userId}")
    public List<AlbumDto> getAlbums(@PathVariable String userId) {
        return albumService.getAlbums(userId);
    }
}
