package com.wine.ecommerce.user.dto;

import com.wine.ecommerce.user.enums.UserRole;

import java.util.UUID;

public record UserRoleDto(
    UUID id,
    UserRole name,
    String displayName,
    String description,
    Boolean isActive
) {}