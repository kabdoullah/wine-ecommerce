package com.wine.ecommerce.user.enums;

import lombok.Getter;

@Getter
public enum UserStatus {
    ACTIVE("Actif"),
    INACTIVE("Inactif"),
    SUSPENDED("Suspendu");

    private final String displayName;

    UserStatus(String displayName) {
        this.displayName = displayName;
    }

}