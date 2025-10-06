package com.wine.ecommerce.core.exceptions;

/**
 * Exception levée en cas d'erreur d'autorisation.
 * 
 * Cette exception est utilisée lorsque l'utilisateur n'a pas
 * les droits suffisants pour effectuer l'opération demandée.
 */
public class UnauthorizedException extends WineEcommerceException {

    /**
     * Constructeur avec code d'erreur.
     *
     * @param errorCode le code d'erreur spécifique
     */
    public UnauthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }

    /**
     * Constructeur avec code d'erreur et arguments.
     *
     * @param errorCode le code d'erreur spécifique
     * @param arguments les arguments pour formater le message
     */
    public UnauthorizedException(ErrorCode errorCode, Object... arguments) {
        super(errorCode, arguments);
    }

    /**
     * Constructeur avec code d'erreur et cause.
     *
     * @param errorCode le code d'erreur spécifique
     * @param cause la cause de l'exception
     */
    public UnauthorizedException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    /**
     * Constructeur avec code d'erreur, cause et arguments.
     *
     * @param errorCode le code d'erreur spécifique
     * @param cause la cause de l'exception
     * @param arguments les arguments pour formater le message
     */
    public UnauthorizedException(ErrorCode errorCode, Throwable cause, Object... arguments) {
        super(errorCode, cause, arguments);
    }
}