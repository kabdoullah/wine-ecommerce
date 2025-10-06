package com.wine.ecommerce.user.dto;

import com.wine.ecommerce.user.enums.UserStatus;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record UserSummaryDto(
    UUID id,
    String email,
    String firstName,
    String lastName,
    UserStatus status,
    Set<String> roleNames,
    LocalDateTime createdAt
) {}