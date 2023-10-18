package com.mtopgul.photoapp.albumservice.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author muhammed-topgul
 * @since 18/10/2023 11:39
 */
@Getter
@Setter
public class AlbumDto {
    private String id;
    private String userId;
    private String name;
    private String description;
}
