package com.wine.ecommerce.config.jwt;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Décodeur JWT personnalisé qui utilise notre JwtUtils existant.
 * Adapte l'interface Spring Security OAuth2 à notre implémentation JWT actuelle.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CustomJwtDecoder implements JwtDecoder {

    private final JwtUtils jwtUtils;

    @Override
    public Jwt decode(String token) throws JwtException {
        try {
            // Valider et extraire les claims avec notre JwtUtils existant
            Claims claims = jwtUtils.parseAndValidateToken(token);
            String subject = jwtUtils.extractUsername(token);

            // Construire les headers (simulés pour compatibilité)
            Map<String, Object> headers = new LinkedHashMap<>();
            headers.put("alg", "HS512");
            headers.put("typ", "JWT");

            // Convertir les claims Jwt en Map
            Map<String, Object> claimsMap = new LinkedHashMap<>();
            claimsMap.put("sub", subject);
            claimsMap.put("iat", claims.getIssuedAt().toInstant());
            claimsMap.put("exp", claims.getExpiration().toInstant());
            
            // Ajouter les rôles si présents dans le token
            if (claims.get("roles") != null) {
                claimsMap.put("roles", claims.get("roles"));
            }
            
            // Ajouter les autres claims custom
            claims.forEach((key, value) -> {
                if (!claimsMap.containsKey(key)) {
                    claimsMap.put(key, value);
                }
            });

            // Créer le JWT Spring Security
            return new Jwt(
                token,
                claims.getIssuedAt().toInstant(),
                claims.getExpiration().toInstant(),
                headers,
                claimsMap
            );

        } catch (Exception e) {
            log.error("Erreur lors du décodage du JWT: {}", e.getMessage());
            throw new JwtException("Échec du décodage JWT", e);
        }
    }
}