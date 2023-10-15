package com.mtopgul.photoapp.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author muhammed-topgul
 * @since 15/10/2023 19:03
 */
@Getter
@Setter
public class LoginDto implements Serializable {
    @NotNull(message = "Email is required")
    @Size(min = 4, max = 20, message = "Email must be between 4 and 20 characters length")
    @Email(message = "Email address must be valid")
    private String email;

    @NotNull(message = "Password is required")
    @Size(min = 4, max = 20, message = "Password must be between 4 and 20 characters length")
    private String password;
}
