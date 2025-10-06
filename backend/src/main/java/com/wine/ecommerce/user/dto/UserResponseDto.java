package com.wine.ecommerce.user.dto;

import com.wine.ecommerce.user.enums.UserStatus;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record UserResponseDto(
    UUID id,
    String email,
    String firstName,
    String lastName,
    String phone,
    UserStatus status,
    Set<UserRoleDto> roles,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}