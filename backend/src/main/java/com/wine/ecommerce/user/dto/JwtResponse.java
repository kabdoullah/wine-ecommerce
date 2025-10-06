package com.wine.ecommerce.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.UUID;

@Schema(description = "Réponse d'authentification avec tokens JWT")
public record JwtResponse(
        @Schema(description = "Token d'accès JWT", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        String token,
        
        @Schema(description = "Token de rafraîchissement", example = "550e8400-e29b-41d4-a716-446655440000")
        String refreshToken,
        
        @Schema(description = "Type de token", example = "Bearer")
        String type,
        
        @Schema(description = "ID de l'utilisateur", example = "123e4567-e89b-12d3-a456-426614174000")
        UUID id,
        
        @Schema(description = "Adresse email de l'utilisateur", example = "user@example.com")
        String email,
        
        @Schema(description = "Prénom de l'utilisateur", example = "Jean")
        String firstName,
        
        @Schema(description = "Nom de famille de l'utilisateur", example = "Dupont")
        String lastName,
        
        @Schema(description = "Liste des rôles de l'utilisateur", example = "[\"CLIENT\", \"ADMIN\"]")
        List<String> roles
) {
    public JwtResponse {
        if (type == null) {
            type = "Bearer";
        }
    }
}