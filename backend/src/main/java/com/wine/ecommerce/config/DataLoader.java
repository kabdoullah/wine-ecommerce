package com.wine.ecommerce.config;

import com.wine.ecommerce.user.entities.Role;
import com.wine.ecommerce.user.entities.User;
import com.wine.ecommerce.user.enums.UserRole;
import com.wine.ecommerce.user.enums.UserStatus;
import com.wine.ecommerce.user.repositories.RoleRepository;
import com.wine.ecommerce.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Chargeur de données initial pour l'application Wine E-commerce.
 * Exécuté au démarrage de l'application pour initialiser les rôles et utilisateurs de base.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements ApplicationRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        log.info("🚀 Initialisation des données de base...");
        
        // Créer les rôles s'ils n'existent pas
        createRoles();
        
        // Créer les utilisateurs de test s'ils n'existent pas
        createTestUsers();
        
        log.info("✅ Initialisation des données terminée avec succès !");
    }

    private void createRoles() {
        log.info("📋 Création des rôles...");
        
        for (UserRole userRole : UserRole.values()) {
            if (!roleRepository.existsByName(userRole)) {
                Role role = new Role(userRole);
                roleRepository.save(role);
                log.info("✅ Rôle créé: {}", userRole.getDisplayName());
            } else {
                log.debug("⚡ Rôle existant: {}", userRole.getDisplayName());
            }
        }
    }

    private void createTestUsers() {
        log.info("👥 Création des utilisateurs de test...");
        
        // Créer un client de test
        createUserIfNotExists(
            "client@wineecommerce.com",
            "Client",
            "Test",
            "0123456789",
            UserRole.CLIENT
        );
        
        // Créer un administrateur
        createUserIfNotExists(
            "admin@wineecommerce.com",
            "Admin",
            "Wine",
            "0156789123",
            UserRole.ADMIN
        );
        
        // Créer un super administrateur
        createUserIfNotExists(
            "superadmin@wineecommerce.com",
            "Super",
            "Admin",
            "0145678912",
            UserRole.SUPER_ADMIN
        );
    }

    private void createUserIfNotExists(String email, String firstName, String lastName, 
                                     String phone, UserRole userRole) {
        if (!userRepository.existsByEmail(email)) {
            // Créer l'utilisateur avec le constructeur par défaut pour éviter les problèmes de Builder
            User user = new User();
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode("password123"));
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPhone(phone);
            user.setStatus(UserStatus.ACTIVE);
            
            // Initialiser la collection roles si nécessaire
            if (user.getRoles() == null) {
                user.setRoles(new java.util.HashSet<>());
            }
            
            // Attribuer le rôle
            Role role = roleRepository.findByName(userRole)
                .orElseThrow(() -> new RuntimeException("Rôle non trouvé: " + userRole));
            
            // Ajouter le rôle directement à la collection (sans méthode bidirectionnelle)
            user.getRoles().add(role);
            
            // Sauvegarder l'utilisateur
            User savedUser = userRepository.save(user);
            log.info("✅ Utilisateur créé: {} ({}) - ID: {}", email, userRole.getDisplayName(), savedUser.getId());
        } else {
            log.debug("⚡ Utilisateur existant: {}", email);
        }
    }
}