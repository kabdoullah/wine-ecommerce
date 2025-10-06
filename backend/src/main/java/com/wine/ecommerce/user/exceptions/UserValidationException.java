package com.wine.ecommerce.user.exceptions;

import com.wine.ecommerce.core.exceptions.ErrorCode;
import com.wine.ecommerce.core.exceptions.ValidationException;

/**
 * Exception levée lors d'erreurs de validation spécifiques aux utilisateurs.
 * 
 * Cette exception est utilisée pour les erreurs de validation des données
 * utilisateur qui ne rentrent pas dans les catégories d'email ou username
 * déjà existants.
 */
public class UserValidationException extends ValidationException {

    /**
     * Constructeur pour validation de mot de passe.
     *
     * @return exception pour mot de passe invalide
     */
    public static UserValidationException invalidPassword() {
        return new UserValidationException(ErrorCode.USER_INVALID_PASSWORD);
    }

    /**
     * Constructeur pour format d'email invalide.
     *
     * @param email l'email au format invalide
     * @return exception pour email invalide
     */
    public static UserValidationException invalidEmailFormat(String email) {
        return new UserValidationException(ErrorCode.USER_INVALID_EMAIL_FORMAT, email);
    }

    /**
     * Constructeur pour données utilisateur manquantes.
     *
     * @param field le champ manquant
     * @return exception pour champ manquant
     */
    public static UserValidationException missingField(String field) {
        return new UserValidationException(ErrorCode.USER_MISSING_REQUIRED_FIELD, field);
    }

    /**
     * Constructeur avec code d'erreur.
     *
     * @param errorCode le code d'erreur spécifique
     */
    private UserValidationException(ErrorCode errorCode) {
        super(errorCode);
    }

    /**
     * Constructeur avec code d'erreur et arguments.
     *
     * @param errorCode le code d'erreur spécifique
     * @param arguments les arguments pour formater le message
     */
    private UserValidationException(ErrorCode errorCode, Object... arguments) {
        super(errorCode, arguments);
    }
}