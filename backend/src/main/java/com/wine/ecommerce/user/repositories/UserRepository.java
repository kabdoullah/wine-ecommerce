package com.wine.ecommerce.user.repositories;

import com.wine.ecommerce.user.entities.User;
import com.wine.ecommerce.user.enums.UserRole;
import com.wine.ecommerce.user.enums.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
    
    Page<User> findByStatus(UserStatus status, Pageable pageable);
    Page<User> findByRoles_Name(UserRole role, Pageable pageable);
    long countByRoles_Name(UserRole role);
}