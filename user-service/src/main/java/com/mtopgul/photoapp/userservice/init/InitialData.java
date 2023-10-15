package com.mtopgul.photoapp.userservice.init;

import com.mtopgul.photoapp.userservice.dto.UserDto;
import com.mtopgul.photoapp.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author muhammed-topgul
 * @since 15/10/2023 20:12
 */
@Component
@RequiredArgsConstructor
public class InitialData implements CommandLineRunner {
    private final UserService userService;

    @Override
    public void run(String... args) {
        UserDto userDto01 = new UserDto();
        userDto01.setFirstName("Muhammed");
        userDto01.setLastName("Topgul");
        userDto01.setEmail("mtopgul@xmail.com");
        userDto01.setPassword("12345678");

        UserDto userDto02 = new UserDto();
        userDto02.setFirstName("John");
        userDto02.setLastName("Doe");
        userDto02.setEmail("jdoe@xmail.com");
        userDto02.setPassword("12345678");

        userService.create(userDto01);
        userService.create(userDto02);
    }
}
