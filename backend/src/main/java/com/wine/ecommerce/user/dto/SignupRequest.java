package com.wine.ecommerce.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignupRequest(
    @NotBlank
    @Size(min = 3, max = 20)
    String firstName,

    @NotBlank
    @Size(min = 3, max = 20)
    String lastName,

    @NotBlank
    @Size(max = 50)
    @Email
    String email,

    @Size(max = 20)
    String phone,

    @NotBlank
    @Size(min = 6, max = 40)
    String password
) {}