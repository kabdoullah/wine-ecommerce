package com.wine.ecommerce.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuration Swagger/OpenAPI pour l'API Wine E-commerce.
 * <p>
 * Cette configuration génère automatiquement la documentation interactive
 * de l'API avec support pour l'authentification JWT.
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI wineEcommerceOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Wine E-commerce API")
                .description("""
                    API REST pour la plateforme de vente de vins en ligne.
                    
                    **Fonctionnalités principales :**
                    - Authentification JWT
                    - Gestion des utilisateurs et rôles
                    - Catalogue de vins
                    - Panier et commandes
                    - Avis clients
                    
                    **Authentification :**
                    Utilisez le endpoint `/auth/login` pour obtenir un token JWT,
                    puis cliquez sur "Authorize" pour l'utiliser dans vos requêtes.
                    """)
                .version("1.0.0")
                .contact(new Contact()
                    .name("Wine E-commerce Team")
                    .email("contact@wineecommerce.com")
                    .url("https://wineecommerce.com"))
                .license(new License()
                    .name("MIT License")
                    .url("https://opensource.org/licenses/MIT")))
            .servers(List.of(
                new Server()
                    .url("http://localhost:8080")
                    .description("Serveur de développement"),
                new Server()
                    .url("https://api.wineecommerce.com")
                    .description("Serveur de production")))
            .components(new Components()
                .addSecuritySchemes("Bearer Authentication", 
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .description("Authentification JWT. Format: 'Bearer {token}'")))
            .addSecurityItem(new SecurityRequirement()
                .addList("Bearer Authentication"));
    }
}