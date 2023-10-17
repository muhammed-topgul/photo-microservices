package com.mtopgul.photoapp.userservice.controller;

import com.mtopgul.photoapp.userservice.dto.ResponseDto;
import com.mtopgul.photoapp.userservice.dto.UserDto;
import com.mtopgul.photoapp.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author muhammed-topgul
 * @since 06/10/2023 10:07
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final Environment environment;
    private final UserService userService;

    @GetMapping("/status/check")
    public String status() {
        return "User Service is running on (%s) port with token: (%s)!"
                .formatted(environment.getProperty("local.server.port"), environment.getProperty("token.secret"));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok(ResponseDto.newSuccess(userService.create(userDto), HttpStatus.CREATED));
    }
}
