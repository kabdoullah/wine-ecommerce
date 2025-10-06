package com.wine.ecommerce.user.entities;

import com.wine.ecommerce.core.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

/**
 * Entité pour gérer les refresh tokens JWT.
 * Permet le renouvellement des access tokens sans nouvelle authentification.
 */
@Entity
@Table(name = "refresh_tokens")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Instant expiryDate;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public boolean isExpired() {
        return Instant.now().isAfter(this.expiryDate);
    }
}