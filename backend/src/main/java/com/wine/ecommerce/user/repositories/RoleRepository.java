package com.wine.ecommerce.user.repositories;

import com.wine.ecommerce.user.entities.Role;
import com.wine.ecommerce.user.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(UserRole name);
    boolean existsByName(UserRole name);
}