package com.mtopgul.photoapp.albumservice.service;

import com.mtopgul.photoapp.albumservice.dto.AlbumDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author muhammed-topgul
 * @since 18/10/2023 11:46
 */
@Service
public class AlbumService {
    private final List<AlbumDto> albums = new ArrayList<>();

    public List<AlbumDto> getAlbums(String userId) {
        AlbumDto albumDto1 = new AlbumDto();
        albumDto1.setId(userId);
        albumDto1.setId(UUID.randomUUID().toString());
        albumDto1.setDescription("Album 1 description");
        albumDto1.setName("Album 1 Name");

        AlbumDto albumDto2 = new AlbumDto();
        albumDto2.setId(userId);
        albumDto2.setId(UUID.randomUUID().toString());
        albumDto2.setDescription("Album 2 description");
        albumDto2.setName("Album 2 Name");

        albums.add(albumDto1);
        albums.add(albumDto2);
        return albums;
    }
}
