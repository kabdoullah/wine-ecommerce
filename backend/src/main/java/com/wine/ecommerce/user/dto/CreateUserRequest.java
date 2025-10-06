package com.wine.ecommerce.user.dto;

import com.wine.ecommerce.user.enums.UserRole;
import com.wine.ecommerce.user.enums.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record CreateUserRequest(
    @NotBlank
    @Size(min = 3, max = 50)
    String firstName,

    @NotBlank
    @Size(min = 3, max = 50)
    String lastName,

    @NotBlank
    @Size(max = 100)
    @Email
    String email,

    @Size(max = 20)
    String phone,

    @NotBlank
    @Size(min = 6, max = 40)
    String password,

    @NotNull
    UserStatus status,

    @NotNull
    @Size(min = 1)
    Set<UserRole> roles
) {}