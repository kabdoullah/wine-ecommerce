package com.wine.ecommerce.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

/**
 * Configuration de la sécurité au niveau des méthodes pour Spring Security 6.5+.
 * 
 * Remplace l'ancienne annotation @EnableGlobalMethodSecurity qui a été supprimée.
 * 
 * Features activées:
 * - @PreAuthorize/@PostAuthorize (prePostEnabled = true par défaut)
 * - @Secured (securedEnabled = true)
 * - JSR-250 annotations comme @RolesAllowed (jsr250Enabled = true)
 */
@Configuration
@EnableMethodSecurity(
    prePostEnabled = true,      // Active @PreAuthorize/@PostAuthorize (défaut: true)
    securedEnabled = true,      // Active @Secured
    jsr250Enabled = true        // Active @RolesAllowed/@PermitAll/@DenyAll
)
public class MethodSecurityConfig {
    
    // La configuration est entièrement basée sur les annotations
    // Pas besoin de beans supplémentaires pour le setup de base
}