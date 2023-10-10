package com.mtopgul.photoapp.userservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mtopgul.photoapp.userservice.annotion.Unique;
import com.mtopgul.photoapp.userservice.entity.UserEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author muhammed-topgul
 * @since 09/10/2023 10:04
 */
@Getter
@Setter
public class UserDto implements Serializable {
    @Size(min = 36, max = 36, message = "Missing or incorrect id")
    private String id;

    @NotNull(message = "First Name is required")
    @Size(min = 4, max = 20, message = "First Name must be between 4 and 20 characters length")
    private String firstName;

    @NotNull(message = "Last Name is required")
    @Size(min = 4, max = 20, message = "Last Name must be between 4 and 20 characters length")
    private String lastName;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password is required")
    @Size(min = 4, max = 20, message = "Password must be between 4 and 20 characters length")
    private String password;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Encrypted Password is required")
    @Size(min = 4, max = 20, message = "Password must be between 4 and 20 characters length")
    private String encryptedPassword;

    @Unique(entity = UserEntity.class, field = "email", message = "Email must be unique")
    @NotNull(message = "Email is required")
    @Size(min = 4, max = 20, message = "Email must be between 4 and 20 characters length")
    @Email(message = "Email address must be valid")
    private String email;
}
