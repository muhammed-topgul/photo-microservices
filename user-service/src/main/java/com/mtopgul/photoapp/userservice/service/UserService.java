package com.mtopgul.photoapp.userservice.service;

import com.mtopgul.photoapp.userservice.dto.UserDto;
import com.mtopgul.photoapp.userservice.entity.UserEntity;
import com.mtopgul.photoapp.userservice.mapper.UserMapper;
import com.mtopgul.photoapp.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author muhammed-topgul
 * @since 09/10/2023 10:35
 */
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder encoder;

    public UserDto create(UserDto userDto) {
        userDto.setEncryptedPassword(encoder.encode(userDto.getPassword()));
        return userMapper.save(userRepository, userDto);
    }

    public UserDto findByEmail(String email) {
        UserEntity userEntity = userRepository.findUserEntityByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
        return userMapper.toDto(userEntity);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDto userDto = findByEmail(email);
        return new User(userDto.getEmail(), userDto.getEncryptedPassword(), new ArrayList<>());
    }
}
