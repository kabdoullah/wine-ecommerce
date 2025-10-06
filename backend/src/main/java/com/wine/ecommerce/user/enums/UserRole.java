package com.wine.ecommerce.user.enums;

import lombok.Getter;

@Getter
public enum UserRole {
    SUPER_ADMIN("ROLE_SUPER_ADMIN", "Super Administrator", "Accès complet au système"),
    ADMIN("ROLE_ADMIN", "Administrator", "Gestion des utilisateurs et produits"),
    CLIENT("ROLE_CLIENT", "Client", "Accès aux fonctionnalités client");

    private final String authority;
    private final String displayName;
    private final String description;

    UserRole(String authority, String displayName, String description) {
        this.authority = authority;
        this.displayName = displayName;
        this.description = description;
    }

}