package com.mtopgul.photoapp.userservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author muhammed-topgul
 * @since 06/10/2023 10:07
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
    @GetMapping("/status/check")
    public String status() {
        return "User Service is up and running!";
    }
}
