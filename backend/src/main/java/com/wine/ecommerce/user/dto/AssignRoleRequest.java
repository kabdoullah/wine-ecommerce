package com.wine.ecommerce.user.dto;

import com.wine.ecommerce.user.enums.UserRole;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AssignRoleRequest(
    @NotNull
    UUID userId,
    
    @NotNull
    UserRole role
) {}