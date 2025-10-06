package com.wine.ecommerce.config.jwt;

import com.wine.ecommerce.config.jwt.exceptions.JwtAuthenticationException;
import com.wine.ecommerce.config.jwt.exceptions.JwtTokenException;
import com.wine.ecommerce.user.services.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtre d'authentification JWT pour l'application Wine E-commerce.
 * <p>
 * Ce filtre intercepte toutes les requêtes HTTP pour extraire et valider
 * les tokens JWT présents dans l'header Authorization.
 * <p>
 * Fonctionnalités :
 * - Extraction sécurisée du token depuis l'header Authorization
 * - Validation du token avec gestion d'erreurs typées
 * - Chargement des détails utilisateur et configuration du SecurityContext
 * - Gestion robuste des erreurs avec logging approprié
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    
    private final JwtUtils jwtUtils;
    private final CustomUserDetailsService userDetailsService;
    private final JwtProperties jwtProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        
        String requestURI = request.getRequestURI();
        
        try {
            String jwt = extractTokenFromRequest(request);
            
            if (jwt != null) {
                authenticateUser(jwt, request);
                logger.debug("Authentification JWT réussie pour la requête: {}", requestURI);
            }
            
        } catch (JwtTokenException e) {
            logger.warn("Erreur de token JWT pour la requête {}: {} - {}", 
                       requestURI, e.getErrorCode().getCode(), e.getMessage());
            // On ne bloque pas la requête, on laisse Spring Security gérer l'absence d'authentification
            
        } catch (JwtAuthenticationException e) {
            logger.warn("Erreur d'authentification JWT pour la requête {}: {} - {}", 
                       requestURI, e.getErrorCode().getCode(), e.getMessage());
            
        } catch (Exception e) {
            logger.error("Erreur inattendue lors de l'authentification JWT pour la requête {}: {}", 
                        requestURI, e.getMessage(), e);
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Authentifie un utilisateur basé sur le token JWT.
     *
     * @param jwt le token JWT à valider
     * @param request la requête HTTP pour les détails d'authentification
     * @throws JwtTokenException si le token est invalide
     * @throws JwtAuthenticationException si l'utilisateur ne peut pas être authentifié
     */
    private void authenticateUser(String jwt, HttpServletRequest request) 
            throws JwtTokenException, JwtAuthenticationException {
        
        // Valider et extraire le nom d'utilisateur du token
        String username = jwtUtils.extractUsername(jwt);
        
        // Vérifier si l'utilisateur n'est pas déjà authentifié
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            
            try {
                // Charger les détails de l'utilisateur
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                
                // Créer le token d'authentification Spring Security
                UsernamePasswordAuthenticationToken authentication = 
                    new UsernamePasswordAuthenticationToken(
                        userDetails, 
                        null, 
                        userDetails.getAuthorities()
                    );
                
                // Ajouter les détails de la requête web
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                // Configurer le contexte de sécurité
                SecurityContextHolder.getContext().setAuthentication(authentication);
                
                logger.debug("Utilisateur '{}' authentifié avec succès avec les rôles: {}", 
                           username, userDetails.getAuthorities());
                
            } catch (UsernameNotFoundException e) {
                logger.warn("Utilisateur '{}' non trouvé lors de l'authentification JWT", username);
                throw JwtAuthenticationException.userNotFound(username);
            }
        }
    }

    /**
     * Extrait le token JWT depuis la requête HTTP.
     *
     * @param request la requête HTTP
     * @return le token JWT ou null si absent/invalide
     */
    private String extractTokenFromRequest(HttpServletRequest request) {
        String headerAuth = request.getHeader(jwtProperties.getHeaderName());
        
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(jwtProperties.getTokenPrefix())) {
            String token = headerAuth.substring(jwtProperties.getTokenPrefix().length());
            
            // Validation basique de la structure du token
            if (StringUtils.hasText(token) && token.split("\\.").length == 3) {
                return token;
            } else {
                logger.debug("Token JWT avec structure invalide détecté dans la requête");
            }
        }
        
        return null;
    }

    /**
     * Détermine si ce filtre doit être appliqué à la requête.
     * 
     * @param request la requête HTTP
     * @return true si le filtre doit être appliqué
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        
        // Ne pas filtrer les endpoints publics d'authentification
        return path.startsWith("/auth/") || 
               path.startsWith("/swagger-ui/") || 
               path.startsWith("/v3/api-docs/") ||
               path.startsWith("/h2-console/") ||
               path.equals("/error");
    }
}