package com.wine.ecommerce.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO pour les requêtes de refresh token.
 */
@Schema(description = "Requête pour rafraîchir un token d'accès")
public record RefreshTokenRequest(
        @Schema(description = "Le refresh token à utiliser pour obtenir un nouveau token d'accès", 
                example = "550e8400-e29b-41d4-a716-446655440000")
        @NotBlank(message = "Le refresh token ne peut pas être vide")
        String refreshToken
) {
}