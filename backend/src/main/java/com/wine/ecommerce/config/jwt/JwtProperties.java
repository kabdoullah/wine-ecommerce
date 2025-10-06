package com.wine.ecommerce.config.jwt;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;

/**
 * Configuration externalisée pour les propriétés JWT.
 * <p>
 * Cette classe centralise toute la configuration JWT de l'application
 * avec validation automatique des paramètres au démarrage.
 */
@Data
@Component
@ConfigurationProperties(prefix = "app.jwt")
@Validated
public class JwtProperties {

    /**
     * Secret utilisé pour signer les tokens JWT.
     * Doit être suffisamment complexe et sécurisé.
     */
    @NotBlank(message = "Le secret JWT ne peut pas être vide")
    private String secret;

    /**
     * Durée d'expiration des tokens JWT en millisecondes.
     * Minimum 5 minutes (300000 ms) pour éviter des tokens trop courts.
     */
    @Min(value = 300000, message = "L'expiration doit être d'au moins 5 minutes")
    private int expiration = 86400000; // 24 heures par défaut

    /**
     * Émetteur (issuer) du token JWT.
     * Identifie l'application qui a émis le token.
     */
    @NotBlank(message = "L'issuer JWT ne peut pas être vide")
    private String issuer = "wine-ecommerce";

    /**
     * Durée d'expiration des refresh tokens en millisecondes.
     * Par défaut 7 jours.
     */
    @Min(value = 86400000, message = "L'expiration du refresh token doit être d'au moins 24 heures")
    private long refreshExpiration = 604800000L; // 7 jours

    /**
     * Préfixe utilisé dans l'header Authorization.
     */
    @NotBlank(message = "Le préfixe du token ne peut pas être vide")
    private String tokenPrefix = "Bearer ";

    /**
     * Nom de l'header contenant le token JWT.
     */
    @NotBlank(message = "Le nom de l'header ne peut pas être vide")
    private String headerName = "Authorization";

    /**
     * Claim personnalisé pour les rôles utilisateur.
     */
    @NotBlank(message = "Le claim des rôles ne peut pas être vide")
    private String rolesClaim = "roles";

    /**
     * Claim personnalisé pour l'ID utilisateur.
     */
    @NotBlank(message = "Le claim de l'ID utilisateur ne peut pas être vide")
    private String userIdClaim = "userId";

    /**
     * Retourne la durée d'expiration sous forme de Duration.
     *
     * @return Duration d'expiration
     */
    public Duration getExpirationDuration() {
        return Duration.ofMillis(expiration);
    }

    /**
     * Retourne la durée d'expiration du refresh token sous forme de Duration.
     *
     * @return Duration d'expiration du refresh token
     */
    public Duration getRefreshExpirationDuration() {
        return Duration.ofMillis(refreshExpiration);
    }
}