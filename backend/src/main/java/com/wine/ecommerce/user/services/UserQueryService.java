package com.wine.ecommerce.user.services;

import com.wine.ecommerce.user.dto.UserResponseDto;
import com.wine.ecommerce.user.dto.UserSummaryDto;
import com.wine.ecommerce.user.enums.UserRole;
import com.wine.ecommerce.user.enums.UserStatus;
import com.wine.ecommerce.user.mappers.UserMapper;
import com.wine.ecommerce.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserQueryService {

    private final UserRepository userRepository;
    private final UserManagementService userManagementService;
    private final UserMapper userMapper;

    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public UserResponseDto getUserDetails(UUID userId) {
        log.debug("Fetching user details for ID: {}", userId);
        return userMapper.toResponseDto(userManagementService.findUserById(userId));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public Page<UserSummaryDto> getAllUsers(Pageable pageable) {
        log.debug("Fetching all users with pagination: {}", pageable);
        return userRepository.findAll(pageable)
                .map(userMapper::toSummaryDto);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public Page<UserSummaryDto> getUsersByStatus(UserStatus status, Pageable pageable) {
        log.debug("Fetching users by status {} with pagination: {}", status, pageable);
        return userRepository.findByStatus(status, pageable)
                .map(userMapper::toSummaryDto);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public Page<UserSummaryDto> getUsersByRole(UserRole role, Pageable pageable) {
        log.debug("Fetching users by role {} with pagination: {}", role, pageable);
        return userRepository.findByRoles_Name(role, pageable)
                .map(userMapper::toSummaryDto);
    }
}