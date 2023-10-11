package com.mtopgul.photoapp.userservice.service;

import com.mtopgul.photoapp.userservice.dto.UserDto;
import com.mtopgul.photoapp.userservice.mapper.UserMapper;
import com.mtopgul.photoapp.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author muhammed-topgul
 * @since 09/10/2023 10:35
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder encoder;

    public UserDto create(UserDto userDto) {
        userDto.setEncryptedPassword(encoder.encode(userDto.getPassword()));
        return userMapper.save(userRepository, userDto);
    }
}
