package com.wine.ecommerce.core.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Énumération des codes d'erreur pour l'application Wine E-commerce.
 * <p>
 * Chaque code d'erreur contient :
 * - Un code unique pour identification
 * - Un message par défaut
 * - Un statut HTTP approprié
 */
@Getter
public enum ErrorCode {

    // ===== ERREURS GÉNÉRALES (GENERAL_xxx) =====
    GENERAL_INTERNAL_ERROR("GENERAL_001", "Une erreur interne s'est produite", HttpStatus.INTERNAL_SERVER_ERROR),
    GENERAL_VALIDATION_ERROR("GENERAL_002", "Erreur de validation des données", HttpStatus.BAD_REQUEST),
    GENERAL_RESOURCE_NOT_FOUND("GENERAL_003", "Ressource non trouvée", HttpStatus.NOT_FOUND),
    GENERAL_CONFLICT("GENERAL_004", "Conflit de données", HttpStatus.CONFLICT),
    GENERAL_UNAUTHORIZED("GENERAL_005", "Accès non autorisé", HttpStatus.UNAUTHORIZED),
    GENERAL_FORBIDDEN("GENERAL_006", "Accès interdit", HttpStatus.FORBIDDEN),

    // ===== ERREURS UTILISATEUR (USER_xxx) =====
    USER_NOT_FOUND("USER_001", "Utilisateur non trouvé avec l'ID : %s", HttpStatus.NOT_FOUND),
    USER_NOT_FOUND_BY_USERNAME("USER_002", "Utilisateur non trouvé avec le nom d'utilisateur : %s", HttpStatus.NOT_FOUND),
    USER_NOT_FOUND_BY_EMAIL("USER_003", "Utilisateur non trouvé avec l'email : %s", HttpStatus.NOT_FOUND),
    USER_EMAIL_ALREADY_EXISTS("USER_004", "Un utilisateur avec l'email '%s' existe déjà", HttpStatus.CONFLICT),
    USER_USERNAME_ALREADY_EXISTS("USER_005", "Un utilisateur avec le nom d'utilisateur '%s' existe déjà", HttpStatus.CONFLICT),
    USER_INVALID_CREDENTIALS("USER_006", "Email ou mot de passe incorrect", HttpStatus.UNAUTHORIZED),
    USER_INSUFFICIENT_PRIVILEGES("USER_007", "Privilèges insuffisants pour cette opération", HttpStatus.FORBIDDEN),
    USER_LAST_SUPER_ADMIN("USER_008", "Impossible de supprimer le dernier super administrateur", HttpStatus.CONFLICT),
    USER_MUST_HAVE_ROLE("USER_009", "L'utilisateur doit avoir au moins un rôle", HttpStatus.BAD_REQUEST),
    USER_INVALID_STATUS("USER_010", "Statut utilisateur invalide : %s", HttpStatus.BAD_REQUEST),
    USER_INVALID_STATUS_FOR_OPERATION("USER_011", "Statut '%s' invalide pour l'opération '%s'", HttpStatus.BAD_REQUEST),
    USER_INVALID_PASSWORD("USER_012", "Le mot de passe ne respecte pas les critères de sécurité", HttpStatus.BAD_REQUEST),
    USER_INVALID_EMAIL_FORMAT("USER_013", "Format d'email invalide : %s", HttpStatus.BAD_REQUEST),
    USER_MISSING_REQUIRED_FIELD("USER_014", "Le champ '%s' est obligatoire", HttpStatus.BAD_REQUEST),

    // ===== ERREURS RÔLE (ROLE_xxx) =====
    ROLE_NOT_FOUND("ROLE_001", "Rôle non trouvé : %s", HttpStatus.NOT_FOUND),
    ROLE_ALREADY_ASSIGNED("ROLE_002", "Le rôle '%s' est déjà assigné à cet utilisateur", HttpStatus.CONFLICT),
    ROLE_NOT_ASSIGNED("ROLE_003", "Le rôle '%s' n'est pas assigné à cet utilisateur", HttpStatus.BAD_REQUEST),
    ROLE_INVALID("ROLE_004", "Rôle invalide : %s", HttpStatus.BAD_REQUEST),

    // ===== ERREURS JWT (JWT_xxx) =====
    JWT_TOKEN_EXPIRED("JWT_001", "Le token JWT a expiré", HttpStatus.UNAUTHORIZED),
    JWT_TOKEN_INVALID("JWT_002", "Token JWT invalide", HttpStatus.UNAUTHORIZED),
    JWT_TOKEN_MISSING("JWT_003", "Token JWT manquant", HttpStatus.UNAUTHORIZED),
    JWT_TOKEN_MALFORMED("JWT_004", "Token JWT mal formé", HttpStatus.UNAUTHORIZED),
    JWT_TOKEN_UNSUPPORTED("JWT_005", "Token JWT non supporté", HttpStatus.UNAUTHORIZED),
    JWT_CLAIMS_EMPTY("JWT_006", "Claims JWT vides", HttpStatus.UNAUTHORIZED),
    JWT_INVALID_SIGNATURE("JWT_007", "Signature JWT invalide", HttpStatus.UNAUTHORIZED),
    JWT_USER_NOT_FOUND("JWT_008", "Utilisateur non trouvé pour le token JWT : %s", HttpStatus.UNAUTHORIZED),
    JWT_AUTHENTICATION_FAILED("JWT_009", "Échec de l'authentification JWT", HttpStatus.UNAUTHORIZED),
    
    // ===== ERREURS REFRESH TOKEN (REFRESH_xxx) =====
    REFRESH_TOKEN_ERROR("REFRESH_001", "Erreur de refresh token", HttpStatus.UNAUTHORIZED),
    REFRESH_TOKEN_NOT_FOUND("REFRESH_002", "Refresh token non trouvé : %s", HttpStatus.UNAUTHORIZED),
    REFRESH_TOKEN_EXPIRED("REFRESH_003", "Refresh token expiré : %s", HttpStatus.UNAUTHORIZED),

    // ===== ERREURS PRODUIT (PRODUCT_xxx) =====
    PRODUCT_NOT_FOUND("PRODUCT_001", "Produit non trouvé avec l'ID : %s", HttpStatus.NOT_FOUND),
    PRODUCT_SKU_ALREADY_EXISTS("PRODUCT_002", "Un produit avec le SKU '%s' existe déjà", HttpStatus.CONFLICT),
    PRODUCT_INSUFFICIENT_STOCK("PRODUCT_003", "Stock insuffisant pour le produit : %s", HttpStatus.BAD_REQUEST),
    PRODUCT_INVALID_PRICE("PRODUCT_004", "Prix invalide pour le produit", HttpStatus.BAD_REQUEST),

    // ===== ERREURS CATÉGORIE (CATEGORY_xxx) =====
    CATEGORY_NOT_FOUND("CATEGORY_001", "Catégorie non trouvée avec l'ID : %s", HttpStatus.NOT_FOUND),
    CATEGORY_SLUG_ALREADY_EXISTS("CATEGORY_002", "Une catégorie avec le slug '%s' existe déjà", HttpStatus.CONFLICT),

    // ===== ERREURS COMMANDE (ORDER_xxx) =====
    ORDER_NOT_FOUND("ORDER_001", "Commande non trouvée avec l'ID : %s", HttpStatus.NOT_FOUND),
    ORDER_CANNOT_MODIFY("ORDER_002", "Impossible de modifier une commande dans l'état : %s", HttpStatus.BAD_REQUEST),
    ORDER_EMPTY_CART("ORDER_003", "Impossible de créer une commande avec un panier vide", HttpStatus.BAD_REQUEST),

    // ===== ERREURS PANIER (CART_xxx) =====
    CART_NOT_FOUND("CART_001", "Panier non trouvé avec l'ID : %s", HttpStatus.NOT_FOUND),
    CART_ITEM_NOT_FOUND("CART_002", "Article non trouvé dans le panier", HttpStatus.NOT_FOUND),
    CART_INVALID_QUANTITY("CART_003", "Quantité invalide : %d", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    /**
     * Constructeur de l'énumération ErrorCode.
     *
     * @param code le code unique de l'erreur
     * @param message le message par défaut
     * @param httpStatus le statut HTTP associé
     */
    ErrorCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }


}