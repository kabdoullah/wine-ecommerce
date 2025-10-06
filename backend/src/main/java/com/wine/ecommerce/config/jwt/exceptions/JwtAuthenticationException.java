package com.wine.ecommerce.config.jwt.exceptions;

import com.wine.ecommerce.core.exceptions.ErrorCode;
import com.wine.ecommerce.core.exceptions.UnauthorizedException;

/**
 * Exception levée lors d'erreurs d'authentification JWT.
 * 
 * Cette exception est utilisée lorsque l'authentification par JWT échoue
 * pour des raisons autres que les problèmes de token (ex: utilisateur non trouvé).
 */
public class JwtAuthenticationException extends UnauthorizedException {

    /**
     * Constructeur pour utilisateur non trouvé.
     *
     * @param username le nom d'utilisateur non trouvé
     * @return exception pour utilisateur non trouvé
     */
    public static JwtAuthenticationException userNotFound(String username) {
        return new JwtAuthenticationException(ErrorCode.JWT_USER_NOT_FOUND, username);
    }

    /**
     * Constructeur pour échec d'authentification.
     *
     * @return exception pour échec d'authentification
     */
    public static JwtAuthenticationException authenticationFailed() {
        return new JwtAuthenticationException(ErrorCode.JWT_AUTHENTICATION_FAILED);
    }

    /**
     * Constructeur pour token manquant.
     *
     * @return exception pour token manquant
     */
    public static JwtAuthenticationException missingToken() {
        return new JwtAuthenticationException(ErrorCode.JWT_TOKEN_MISSING);
    }

    /**
     * Constructeur avec code d'erreur.
     *
     * @param errorCode le code d'erreur spécifique
     */
    private JwtAuthenticationException(ErrorCode errorCode) {
        super(errorCode);
    }

    /**
     * Constructeur avec code d'erreur et arguments.
     *
     * @param errorCode le code d'erreur spécifique
     * @param arguments les arguments pour formater le message
     */
    private JwtAuthenticationException(ErrorCode errorCode, Object... arguments) {
        super(errorCode, arguments);
    }
}