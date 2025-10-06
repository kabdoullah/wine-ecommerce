package com.wine.ecommerce.user.services;

import com.wine.ecommerce.user.dto.SignupRequest;
import com.wine.ecommerce.user.entities.Role;
import com.wine.ecommerce.user.entities.User;
import com.wine.ecommerce.user.enums.UserRole;
import com.wine.ecommerce.user.enums.UserStatus;
import com.wine.ecommerce.user.exceptions.EmailAlreadyExistsException;
import com.wine.ecommerce.user.exceptions.RoleNotFoundException;
import com.wine.ecommerce.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserRegistrationService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(SignupRequest signUpRequest) {
        log.info("Registering new user with email: {}", signUpRequest.email());
        
        validateEmailUniqueness(signUpRequest.email());
        
        User user = createUserFromRequest(signUpRequest);
        assignDefaultRole(user);
        
        userRepository.save(user);
        
        log.info("User registered successfully with email: {}", signUpRequest.email());
    }

    private void validateEmailUniqueness(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException(email);
        }
    }

    private User createUserFromRequest(SignupRequest request) {
        return User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .phone(request.phone())
                .password(passwordEncoder.encode(request.password()))
                .status(UserStatus.ACTIVE)
                .build();
    }

    private void assignDefaultRole(User user) {
        try {
            Role clientRole = roleService.findRoleByName(UserRole.CLIENT);
            user.addRole(clientRole);
        } catch (RoleNotFoundException e) {
            log.error("CLIENT role not found during user registration", e);
            throw new IllegalStateException("System configuration error: CLIENT role not found", e);
        }
    }
}