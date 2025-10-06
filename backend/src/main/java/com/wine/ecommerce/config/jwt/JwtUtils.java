package com.wine.ecommerce.config.jwt;

import com.wine.ecommerce.config.jwt.exceptions.JwtTokenException;
import com.wine.ecommerce.user.entities.UserPrincipal;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Utilitaire pour la gestion des tokens JWT dans l'application Wine E-commerce.
 * 
 * Cette classe fournit toutes les fonctionnalités nécessaires pour :
 * - Génération de tokens JWT avec claims personnalisés
 * - Validation et parsing sécurisé des tokens
 * - Extraction d'informations utilisateur depuis les tokens
 * - Gestion des erreurs avec exceptions typées
 */
@Component
@RequiredArgsConstructor
public class JwtUtils {
    
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    
    private final JwtProperties jwtProperties;

    /**
     * Génère la clé de signature sécurisée.
     *
     * @return SecretKey pour signer les tokens
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    /**
     * Génère un token JWT à partir d'une authentification.
     *
     * @param authentication l'objet d'authentification Spring Security
     * @return token JWT signé
     * @throws IllegalArgumentException si l'authentification est null
     */
    public String generateToken(Authentication authentication) {
        validateAuthentication(authentication);
        
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Map<String, Object> claims = buildClaimsFromUserDetails(userDetails);
        
        return generateTokenWithClaims(userDetails.getUsername(), claims);
    }

    /**
     * Génère un token JWT à partir d'un nom d'utilisateur.
     *
     * @param username le nom d'utilisateur
     * @return token JWT signé
     * @throws IllegalArgumentException si le username est null ou vide
     */
    public String generateTokenFromUsername(String username) {
        validateUsername(username);
        return generateTokenWithClaims(username, Collections.emptyMap());
    }

    /**
     * Génère un token JWT avec des claims personnalisés.
     *
     * @param username le nom d'utilisateur (subject)
     * @param customClaims les claims personnalisés à ajouter
     * @return token JWT signé
     * @throws IllegalArgumentException si les paramètres sont invalides
     */
    public String generateTokenWithClaims(String username, Map<String, Object> customClaims) {
        validateUsername(username);
        
        Instant now = Instant.now();
        Instant expiration = now.plus(jwtProperties.getExpirationDuration());
        
        JwtBuilder builder = Jwts.builder()
                .setSubject(username)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256);

        // Ajouter les claims personnalisés
        if (customClaims != null && !customClaims.isEmpty()) {
            customClaims.forEach(builder::claim);
        }

        String token = builder.compact();
        
        logger.debug("Token JWT généré pour l'utilisateur: {} avec expiration: {}", username, expiration);
        return token;
    }

    /**
     * Génère un refresh token.
     *
     * @param username le nom d'utilisateur
     * @return refresh token JWT
     * @throws IllegalArgumentException si le username est invalide
     */
    public String generateRefreshToken(String username) {
        validateUsername(username);
        
        Instant now = Instant.now();
        Instant expiration = now.plus(jwtProperties.getRefreshExpirationDuration());
        
        String refreshToken = Jwts.builder()
                .setSubject(username)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiration))
                .claim("type", "refresh")
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
        
        logger.debug("Refresh token généré pour l'utilisateur: {} avec expiration: {}", username, expiration);
        return refreshToken;
    }

    /**
     * Parse et valide un token JWT, retournant les claims.
     *
     * @param token le token JWT à valider
     * @return les claims du token
     * @throws JwtTokenException si le token est invalide
     */
    public Claims parseAndValidateToken(String token) throws JwtTokenException {
        validateTokenInput(token);
        
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .requireIssuer(jwtProperties.getIssuer())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            
            logger.debug("Token JWT validé avec succès pour l'utilisateur: {}", claims.getSubject());
            return claims;
            
        } catch (ExpiredJwtException e) {
            logger.warn("Token JWT expiré pour l'utilisateur: {}", e.getClaims().getSubject());
            throw JwtTokenException.expired();
        } catch (MalformedJwtException e) {
            logger.warn("Token JWT malformé: {}", e.getMessage());
            throw JwtTokenException.malformed();
        } catch (SignatureException e) {
            logger.warn("Signature JWT invalide: {}", e.getMessage());
            throw JwtTokenException.invalidSignature();
        } catch (UnsupportedJwtException e) {
            logger.warn("Token JWT non supporté: {}", e.getMessage());
            throw JwtTokenException.unsupported();
        } catch (IllegalArgumentException e) {
            logger.warn("Claims JWT vides: {}", e.getMessage());
            throw JwtTokenException.emptyClaims();
        } catch (Exception e) {
            logger.error("Erreur inattendue lors de la validation du token JWT", e);
            throw JwtTokenException.invalid();
        }
    }

    /**
     * Extrait le nom d'utilisateur depuis un token JWT.
     *
     * @param token le token JWT
     * @return le nom d'utilisateur (subject)
     * @throws JwtTokenException si le token est invalide
     */
    public String extractUsername(String token) throws JwtTokenException {
        Claims claims = parseAndValidateToken(token);
        return claims.getSubject();
    }

    /**
     * Extrait les rôles utilisateur depuis un token JWT.
     *
     * @param token le token JWT
     * @return liste des rôles de l'utilisateur
     * @throws JwtTokenException si le token est invalide
     */
    @SuppressWarnings("unchecked")
    public List<String> extractRoles(String token) throws JwtTokenException {
        Claims claims = parseAndValidateToken(token);
        Object rolesObj = claims.get(jwtProperties.getRolesClaim());
        
        if (rolesObj instanceof List) {
            return (List<String>) rolesObj;
        } else if (rolesObj instanceof String) {
            return Arrays.asList(rolesObj.toString().split(","));
        }
        
        return Collections.emptyList();
    }

    /**
     * Extrait l'ID utilisateur depuis un token JWT.
     *
     * @param token le token JWT
     * @return l'ID de l'utilisateur ou null si absent
     * @throws JwtTokenException si le token est invalide
     */
    public Long extractUserId(String token) throws JwtTokenException {
        Claims claims = parseAndValidateToken(token);
        Object userIdObj = claims.get(jwtProperties.getUserIdClaim());
        
        if (userIdObj instanceof Number) {
            return ((Number) userIdObj).longValue();
        } else if (userIdObj instanceof String) {
            try {
                return Long.parseLong((String) userIdObj);
            } catch (NumberFormatException e) {
                logger.warn("ID utilisateur invalide dans le token: {}", userIdObj);
            }
        }
        
        return null;
    }

    /**
     * Vérifie si un token est expiré.
     *
     * @param token le token JWT
     * @return true si le token est expiré
     */
    public boolean isTokenExpired(String token) {
        try {
            Claims claims = parseAndValidateToken(token);
            return claims.getExpiration().before(new Date());
        } catch (JwtTokenException e) {
            // Si le token est invalide, on considère qu'il est "expiré"
            return true;
        }
    }

    /**
     * Extrait la date d'expiration d'un token.
     *
     * @param token le token JWT
     * @return la date d'expiration
     * @throws JwtTokenException si le token est invalide
     */
    public Date extractExpirationDate(String token) throws JwtTokenException {
        Claims claims = parseAndValidateToken(token);
        return claims.getExpiration();
    }

    /**
     * Valide qu'un token peut être rafraîchi.
     *
     * @param refreshToken le refresh token
     * @return true si le refresh token est valide
     */
    public boolean canRefreshToken(String refreshToken) {
        try {
            Claims claims = parseAndValidateToken(refreshToken);
            return "refresh".equals(claims.get("type"));
        } catch (JwtTokenException e) {
            return false;
        }
    }

    // === MÉTHODES PRIVÉES DE VALIDATION ET UTILITAIRES ===

    /**
     * Construit les claims depuis un UserDetails.
     */
    private Map<String, Object> buildClaimsFromUserDetails(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        
        // Ajouter les rôles
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .map(role -> role.startsWith("ROLE_") ? role.substring(5) : role)
                .collect(Collectors.toList());
        claims.put(jwtProperties.getRolesClaim(), roles);
        
        // Ajouter l'ID utilisateur si disponible
        if (userDetails instanceof UserPrincipal userPrincipal) {
            claims.put(jwtProperties.getUserIdClaim(), userPrincipal.getId());
        }
        
        return claims;
    }

    /**
     * Valide l'objet Authentication.
     */
    private void validateAuthentication(Authentication authentication) {
        if (authentication == null) {
            throw new IllegalArgumentException("L'objet Authentication ne peut pas être null");
        }
        if (authentication.getPrincipal() == null) {
            throw new IllegalArgumentException("Le principal de l'Authentication ne peut pas être null");
        }
        if (!(authentication.getPrincipal() instanceof UserDetails)) {
            throw new IllegalArgumentException("Le principal doit être une instance de UserDetails");
        }
    }

    /**
     * Valide le nom d'utilisateur.
     */
    private void validateUsername(String username) {
        if (!StringUtils.hasText(username)) {
            throw new IllegalArgumentException("Le nom d'utilisateur ne peut pas être null ou vide");
        }
    }

    /**
     * Valide l'entrée du token.
     */
    private void validateTokenInput(String token) {
        if (!StringUtils.hasText(token)) {
            throw JwtTokenException.invalid();
        }
    }
}