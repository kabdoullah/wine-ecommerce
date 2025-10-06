package com.wine.ecommerce.user.services;

import com.wine.ecommerce.user.dto.*;
import com.wine.ecommerce.user.entities.User;
import com.wine.ecommerce.user.enums.UserRole;
import com.wine.ecommerce.user.enums.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserManagementService userManagementService;
    private final UserQueryService userQueryService;
    private final RoleService roleService;

    public UserResponseDto createUser(CreateUserRequest request) {
        return userManagementService.createUser(request);
    }

    public UserResponseDto updateUser(UUID userId, UpdateUserRequest request) {
        return userManagementService.updateUser(userId, request);
    }

    public void assignRole(UUID userId, UserRole roleToAssign) {
        roleService.assignRole(userId, roleToAssign);
    }

    public void removeRole(UUID userId, UserRole roleToRemove) {
        roleService.removeRole(userId, roleToRemove);
    }

    public void changeUserStatus(UUID userId, UserStatus newStatus) {
        userManagementService.changeUserStatus(userId, newStatus);
    }

    public UserResponseDto getUserDetails(UUID userId) {
        return userQueryService.getUserDetails(userId);
    }

    public Page<UserSummaryDto> getAllUsers(Pageable pageable) {
        return userQueryService.getAllUsers(pageable);
    }

    public Page<UserSummaryDto> getUsersByStatus(UserStatus status, Pageable pageable) {
        return userQueryService.getUsersByStatus(status, pageable);
    }

    public Page<UserSummaryDto> getUsersByRole(UserRole role, Pageable pageable) {
        return userQueryService.getUsersByRole(role, pageable);
    }

    public void deleteUser(UUID userId) {
        userManagementService.deleteUser(userId);
    }

    public User findUserById(UUID userId) {
        return userManagementService.findUserById(userId);
    }

    public User findUserByEmail(String email) {
        return userManagementService.findUserByEmail(email);
    }
}