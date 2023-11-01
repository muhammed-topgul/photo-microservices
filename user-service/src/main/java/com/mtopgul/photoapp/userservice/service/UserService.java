package com.mtopgul.photoapp.userservice.service;

import com.mtopgul.photoapp.userservice.dto.AlbumDto;
import com.mtopgul.photoapp.userservice.dto.UserDto;
import com.mtopgul.photoapp.userservice.entity.UserEntity;
import com.mtopgul.photoapp.userservice.mapper.UserMapper;
import com.mtopgul.photoapp.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author muhammed-topgul
 * @since 09/10/2023 10:35
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder encoder;
    private final AlbumServiceClient albumServiceClient;

    public UserDto create(UserDto userDto) {
        userDto.setEncryptedPassword(encoder.encode(userDto.getPassword()));
        return userMapper.save(userRepository, userDto);
    }

    public UserDto findByEmail(String email) {
        UserEntity userEntity = userRepository.findUserEntityByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
        return userMapper.toDto(userEntity);
    }

    public UserDto findById(String id, String authorization) {
        UserEntity userEntity = userRepository.findUserEntityById(id)
                .orElseThrow(() -> new UsernameNotFoundException(id));
        log.debug("Before calling Album Service");
        List<AlbumDto> albums = albumServiceClient.getAlbums(id, authorization);
        log.debug("After called Album Service");
        return userMapper.toDto(userEntity, albums);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findUserEntityByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        userEntity.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
            role.getAuthorities().forEach(authority -> authorities.add(new SimpleGrantedAuthority(authority.getName())));
        });
        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), authorities);
    }
}
