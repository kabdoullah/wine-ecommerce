package com.wine.ecommerce.user.dto;

import com.wine.ecommerce.user.enums.UserRole;
import com.wine.ecommerce.user.enums.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record UpdateUserRequest(
    @Size(min = 3, max = 50)
    String firstName,

    @Size(min = 3, max = 50)
    String lastName,

    @Size(max = 100)
    @Email
    String email,

    @Size(max = 20)
    String phone,

    UserStatus status,

    Set<UserRole> roles
) {}