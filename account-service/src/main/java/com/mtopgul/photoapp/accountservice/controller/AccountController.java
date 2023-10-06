package com.mtopgul.photoapp.accountservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author muhammed-topgul
 * @since 06/10/2023 10:07
 */
@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    @GetMapping("/status/check")
    public String status() {
        return "Account Service is up and running!";
    }
}
