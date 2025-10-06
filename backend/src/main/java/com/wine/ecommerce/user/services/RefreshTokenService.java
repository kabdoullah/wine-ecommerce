package com.wine.ecommerce.user.services;

import com.wine.ecommerce.user.entities.RefreshToken;
import com.wine.ecommerce.user.entities.User;
import com.wine.ecommerce.user.exceptions.RefreshTokenException;
import com.wine.ecommerce.user.repositories.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

/**
 * Service pour la gestion des refresh tokens.
 */
@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${app.jwtRefreshExpirationMs:86400000}") // 24 heures par défaut
    private Long refreshTokenDurationMs;

    /**
     * Crée un nouveau refresh token pour un utilisateur.
     * Supprime l'ancien token s'il existe.
     */
    @Transactional
    public RefreshToken createRefreshToken(User user) {
        // Supprimer l'ancien refresh token s'il existe
        refreshTokenRepository.findByUser(user)
                .ifPresent(refreshTokenRepository::delete);

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(refreshTokenDurationMs))
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    /**
     * Vérifie la validité d'un refresh token.
     */
    @Transactional
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.isExpired()) {
            refreshTokenRepository.delete(token);
            throw RefreshTokenException.tokenExpired(token.getToken());
        }
        return token;
    }

    /**
     * Trouve un refresh token par sa valeur.
     */
    @Transactional(readOnly = true)
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    /**
     * Supprime un refresh token par utilisateur.
     */
    @Transactional
    public void deleteByUser(User user) {
        refreshTokenRepository.deleteByUser(user);
    }

    /**
     * Supprime tous les tokens expirés.
     */
    @Transactional
    public void deleteExpiredTokens() {
        refreshTokenRepository.deleteExpiredTokens();
    }
}