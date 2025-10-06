package com.wine.ecommerce.user.services;

import com.wine.ecommerce.user.constants.UserConstants;
import com.wine.ecommerce.user.entities.Role;
import com.wine.ecommerce.user.entities.User;
import com.wine.ecommerce.user.enums.UserRole;
import com.wine.ecommerce.user.exceptions.InsufficientRoleException;
import com.wine.ecommerce.user.exceptions.RoleNotFoundException;
import com.wine.ecommerce.user.repositories.RoleRepository;
import com.wine.ecommerce.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public void assignRole(UUID userId, UserRole roleToAssign) {
        log.info("Assigning role {} to user {}", roleToAssign, userId);
        
        User user = findUserById(userId);
        Role role = findRoleByName(roleToAssign);
        
        user.addRole(role);
        userRepository.save(user);
        
        log.info("Role {} assigned successfully to user {}", roleToAssign, userId);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public void removeRole(UUID userId, UserRole roleToRemove) {
        log.info("Removing role {} from user {}", roleToRemove, userId);
        
        User user = findUserById(userId);
        Role role = findRoleByName(roleToRemove);
        
        validateRoleRemoval(user);
        
        user.removeRole(role);
        userRepository.save(user);
        
        log.info("Role {} removed successfully from user {}", roleToRemove, userId);
    }

    @Transactional(readOnly = true)
    public Role findRoleByName(UserRole roleName) {
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> new RoleNotFoundException(
                    String.format(UserConstants.Messages.ROLE_NOT_FOUND, roleName)));
    }

    @Transactional(readOnly = true)
    public Set<Role> findRolesByNames(Set<UserRole> roleNames) {
        Set<Role> roles = new HashSet<>();
        for (UserRole roleName : roleNames) {
            roles.add(findRoleByName(roleName));
        }
        return roles;
    }

    @Transactional(readOnly = true)
    public List<Role> getAllRoles() {
        log.debug("Fetching all roles");
        return roleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Role> getAllActiveRoles() {
        log.debug("Fetching all active roles");
        return roleRepository.findAll().stream()
                .filter(Role::getIsActive)
                .toList();
    }

    private User findUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new com.wine.ecommerce.user.exceptions.UserNotFoundException(userId));
    }

    private void validateRoleRemoval(User user) {
        if (user.getRoles().size() <= 1) {
            throw new InsufficientRoleException(UserConstants.Messages.USER_MUST_HAVE_AT_LEAST_ONE_ROLE);
        }
    }
}