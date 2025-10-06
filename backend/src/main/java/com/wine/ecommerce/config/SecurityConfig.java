package com.wine.ecommerce.config;

import com.wine.ecommerce.config.jwt.CustomJwtDecoder;
import com.wine.ecommerce.config.jwt.JwtAuthenticationConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Configuration de sécurité moderne Spring Security 6.5+ avec OAuth2 Resource Server.
 * <p>
 * Features:
 * - OAuth2 Resource Server natif pour JWT
 * - CORS configuré pour les clients frontend
 * - Autorisation basée sur les rôles (CLIENT, ADMIN, SUPER_ADMIN)
 * - Protection CSRF désactivée pour l'API REST
 * - Sessions stateless pour l'architecture JWT
 * - Headers de sécurité modernes
 * - Method Security activée via @EnableMethodSecurity
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomJwtDecoder customJwtDecoder;
    private final JwtAuthenticationConverter jwtAuthenticationConverter;

    /**
     * Encodeur de mot de passe BCrypt
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * Gestionnaire d'authentification pour les endpoints d'authentification.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Configuration CORS pour permettre les requêtes cross-origin.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Origines autorisées (remplacer par des domaines spécifiques en production)
        configuration.setAllowedOriginPatterns(List.of("http://localhost:*", "https://localhost:*"));

        // Méthodes HTTP autorisées
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));

        // Headers autorisés
        configuration.setAllowedHeaders(List.of("*"));

        // Headers exposés au client
        configuration.setExposedHeaders(List.of("Authorization", "X-Total-Count"));

        // Permettre les credentials (cookies, authorization headers)
        configuration.setAllowCredentials(true);

        // Temps de cache pour les requêtes preflight
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Chaîne de filtres de sécurité moderne avec OAuth2 Resource Server pour JWT.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/api/**", "/auth/**")
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .decoder(customJwtDecoder)
                                .jwtAuthenticationConverter(jwtAuthenticationConverter)
                        )
                        .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                        .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
                )
                .headers(headers -> headers
                        .httpStrictTransportSecurity(hstsConfig -> hstsConfig
                                .maxAgeInSeconds(31536000)
                                .includeSubDomains(true)
                        )
                )
                .authorizeHttpRequests(authz -> authz
                        // Endpoints d'authentification - accès public
                        .requestMatchers("/auth/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()

                        // Endpoints utilisateurs - gestion des rôles
                        .requestMatchers("/api/users", "/api/users/*/roles/**", "/api/users/*/status").hasAnyRole("ADMIN", "SUPER_ADMIN")
                        .requestMatchers("/api/users/*/delete").hasRole("SUPER_ADMIN")

                        // Endpoints rôles - accès administrateur
                        .requestMatchers("/api/roles/**").hasAnyRole("ADMIN", "SUPER_ADMIN")

                        // Endpoints commandes admin - accès administrateur
                        .requestMatchers("/api/orders/admin/**").hasAnyRole("ADMIN", "SUPER_ADMIN")

                        // Endpoints utilisateur authentifié
                        .requestMatchers("/api/profile/**", "/api/orders/**", "/api/cart/**").authenticated()

                        // Autres endpoints API - authentification requise
                        .requestMatchers("/api/**").authenticated()
                )
                .build();
    }

}