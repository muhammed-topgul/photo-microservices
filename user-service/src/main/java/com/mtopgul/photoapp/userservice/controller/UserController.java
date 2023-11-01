package com.mtopgul.photoapp.userservice.controller;

import com.mtopgul.photoapp.userservice.dto.ResponseDto;
import com.mtopgul.photoapp.userservice.dto.UserDto;
import com.mtopgul.photoapp.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
    public ResponseEntity<ResponseDto.Response> signUp(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok(ResponseDto.newSuccess(userService.create(userDto), HttpStatus.CREATED));
    }

    @PreAuthorize("principal == #id")
    @PostAuthorize("principal == returnObject.body.body.id")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto.Response> findById(@PathVariable String id,
                                                         @RequestHeader("Authorization") String authorization) {
        return ResponseEntity.ok(ResponseDto.newSuccess(userService.findById(id, authorization), HttpStatus.ACCEPTED));
    }

    @PreAuthorize("hasRole('ADMIN') or hasAuthority('PROFILE_DELETE')")
    @GetMapping("/delete-user/{id}")
    public ResponseEntity<ResponseDto.Response> deleteUser(@PathVariable String id) {
        return ResponseEntity.ok(ResponseDto.newSuccess("User deleted: %s".formatted(id), HttpStatus.OK));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/only-for-admin")
    public ResponseEntity<ResponseDto.Response> onlyForAdmin() {
        return ResponseEntity.ok(ResponseDto.newSuccess("This message only available for ADMINs", HttpStatus.OK));
    }

    @PreAuthorize("hasRole('ADMIN') or principal == #id")
    @GetMapping("/for-admin-or-authenticated/{id}")
    public ResponseEntity<ResponseDto.Response> forAdminOrAuthenticated(@PathVariable String id) {
        return ResponseEntity.ok(ResponseDto.newSuccess("This message available for ADMINs and all authenticated USERs.", HttpStatus.OK));
    }
}
