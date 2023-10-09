package com.mtopgul.photoapp.userservice.mapper;

import com.mtopgul.photoapp.userservice.dto.UserDto;
import com.mtopgul.photoapp.userservice.entity.UserEntity;
import org.mapstruct.Mapper;

/**
 * @author muhammed-topgul
 * @since 09/10/2023 10:02
 */
@Mapper(componentModel = "spring")
public abstract class UserMapper extends AbstractMapper<UserDto, UserEntity> {
}