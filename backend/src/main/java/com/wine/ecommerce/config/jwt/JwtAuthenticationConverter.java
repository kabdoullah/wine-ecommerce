package com.wine.ecommerce.config.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Convertit un JWT en token d'authentification Spring Security avec les autorités appropriées.
 * Mappe les rôles du JWT vers les autorités Spring Security avec le préfixe ROLE_.
 */
@Slf4j
@Component
public class JwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private static final String ROLES_CLAIM = "roles";
    private static final String AUTHORITIES_CLAIM = "authorities";
    private static final String ROLE_PREFIX = "ROLE_";

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = extractAuthorities(jwt);
        return new JwtAuthenticationToken(jwt, authorities);
    }

    /**
     * Extrait les autorités du JWT.
     * Supporte les claims 'roles' et 'authorities'.
     */
    private Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        // Essayer d'abord les rôles
        Collection<GrantedAuthority> roleAuthorities = extractRoles(jwt);
        if (!roleAuthorities.isEmpty()) {
            return roleAuthorities;
        }

        // Puis essayer les autorités directes
        Collection<GrantedAuthority> directAuthorities = extractDirectAuthorities(jwt);
        if (!directAuthorities.isEmpty()) {
            return directAuthorities;
        }

        log.debug("Aucune autorité trouvée dans le JWT pour le sujet: {}", jwt.getSubject());
        return Collections.emptyList();
    }

    /**
     * Extrait les rôles du claim 'roles' et les convertit en autorités avec préfixe ROLE_.
     */
    private Collection<GrantedAuthority> extractRoles(Jwt jwt) {
        Object rolesClaim = jwt.getClaim(ROLES_CLAIM);
        
        if (rolesClaim instanceof List<?> rolesList) {
            return rolesList.stream()
                    .filter(String.class::isInstance)
                    .map(String.class::cast)
                    .map(role -> new SimpleGrantedAuthority(ensureRolePrefix(role)))
                    .collect(Collectors.toList());
        }
        
        if (rolesClaim instanceof String singleRole) {
            return List.of(new SimpleGrantedAuthority(ensureRolePrefix(singleRole)));
        }
        
        return Collections.emptyList();
    }

    /**
     * Extrait les autorités directes du claim 'authorities'.
     */
    private Collection<GrantedAuthority> extractDirectAuthorities(Jwt jwt) {
        Object authoritiesClaim = jwt.getClaim(AUTHORITIES_CLAIM);
        
        if (authoritiesClaim instanceof List<?> authoritiesList) {
            return authoritiesList.stream()
                    .filter(String.class::isInstance)
                    .map(String.class::cast)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }
        
        if (authoritiesClaim instanceof String singleAuthority) {
            return List.of(new SimpleGrantedAuthority(singleAuthority));
        }
        
        return Collections.emptyList();
    }

    /**
     * Assure que le rôle a le préfixe ROLE_ requis par Spring Security.
     */
    private String ensureRolePrefix(String role) {
        if (role == null || role.trim().isEmpty()) {
            return ROLE_PREFIX;
        }
        
        String trimmedRole = role.trim().toUpperCase();
        return trimmedRole.startsWith(ROLE_PREFIX) ? trimmedRole : ROLE_PREFIX + trimmedRole;
    }
}