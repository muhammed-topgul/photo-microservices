package com.mtopgul.photoapp.userservice.dto;

import com.mtopgul.photoapp.userservice.annotion.Unique;
import com.mtopgul.photoapp.userservice.entity.UserEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * @author muhammed-topgul
 * @since 09/10/2023 10:04
 */
@Getter
@Setter
public class UserDto {
    private String id;

    @NotNull(message = "First Name is required")
    @Size(min = 4, max = 20, message = "First Name must be between 4 and 20 characters length")
    private String firstName;

    @NotNull(message = "Last Name is required")
    @Size(min = 4, max = 20, message = "Last Name must be between 4 and 20 characters length")
    private String lastName;

    @NotNull(message = "Password is required")
    @Size(min = 4, max = 20, message = "Password must be between 4 and 20 characters length")
    private String password;

    @Unique(entity = UserEntity.class, field = "email", message = "Email must be unique")
    @NotNull(message = "Email is required")
    @Size(min = 4, max = 20, message = "Email must be between 4 and 20 characters length")
    @Email(message = "Email address must be valid")
    private String email;
}
