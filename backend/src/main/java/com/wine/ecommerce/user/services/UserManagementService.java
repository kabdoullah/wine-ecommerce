package com.wine.ecommerce.user.services;

import com.wine.ecommerce.user.constants.UserConstants;
import com.wine.ecommerce.user.dto.*;
import com.wine.ecommerce.user.entities.User;
import com.wine.ecommerce.user.enums.UserRole;
import com.wine.ecommerce.user.enums.UserStatus;
import com.wine.ecommerce.user.exceptions.InsufficientRoleException;
import com.wine.ecommerce.user.exceptions.UserNotFoundException;
import com.wine.ecommerce.user.mappers.UserMapper;
import com.wine.ecommerce.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserManagementService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public UserResponseDto createUser(CreateUserRequest request) {
        log.info("Creating user with email: {}", request.email());
        
        validateEmailUniqueness(request.email());
        
        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRoles(roleService.findRolesByNames(request.roles()));

        User savedUser = userRepository.save(user);
        log.info("User created successfully with ID: {}", savedUser.getId());
        
        return userMapper.toResponseDto(savedUser);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public UserResponseDto updateUser(UUID userId, UpdateUserRequest request) {
        log.info("Updating user with ID: {}", userId);
        
        User user = findUserById(userId);
        userMapper.updateEntityFromDto(request, user);
        
        if (request.roles() != null) {
            user.setRoles(roleService.findRolesByNames(request.roles()));
        }

        User updatedUser = userRepository.save(user);
        log.info("User updated successfully with ID: {}", userId);
        
        return userMapper.toResponseDto(updatedUser);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public void changeUserStatus(UUID userId, UserStatus newStatus) {
        log.info("Changing status for user {} to {}", userId, newStatus);
        
        User user = findUserById(userId);
        user.setStatus(newStatus);
        userRepository.save(user);
        
        log.info("User status changed successfully for ID: {}", userId);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public void deleteUser(UUID userId) {
        log.info("Deleting user with ID: {}", userId);
        
        User user = findUserById(userId);
        
        validateSuperAdminDeletion(user);
        
        userRepository.delete(user);
        log.info("User deleted successfully with ID: {}", userId);
    }

    @Transactional(readOnly = true)
    public User findUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Transactional(readOnly = true)
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> UserNotFoundException.byEmail(email));
    }

    private void validateEmailUniqueness(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException(UserConstants.Messages.EMAIL_ALREADY_EXISTS);
        }
    }

    private void validateSuperAdminDeletion(User user) {
        if (user.hasRole(UserRole.SUPER_ADMIN)) {
            long superAdminCount = userRepository.countByRoles_Name(UserRole.SUPER_ADMIN);
            if (superAdminCount <= 1) {
                throw new InsufficientRoleException(UserConstants.Messages.CANNOT_DELETE_LAST_SUPER_ADMIN);
            }
        }
    }
}