package com.wine.ecommerce.core.exceptions;

/**
 * Exception levée en cas d'erreur de validation des données.
 * 
 * Cette exception est utilisée lorsque les données fournies
 * ne respectent pas les règles de validation définies.
 */
public class ValidationException extends WineEcommerceException {

    /**
     * Constructeur avec code d'erreur.
     *
     * @param errorCode le code d'erreur spécifique
     */
    public ValidationException(ErrorCode errorCode) {
        super(errorCode);
    }

    /**
     * Constructeur avec code d'erreur et arguments.
     *
     * @param errorCode le code d'erreur spécifique
     * @param arguments les arguments pour formater le message
     */
    public ValidationException(ErrorCode errorCode, Object... arguments) {
        super(errorCode, arguments);
    }

    /**
     * Constructeur avec code d'erreur et cause.
     *
     * @param errorCode le code d'erreur spécifique
     * @param cause la cause de l'exception
     */
    public ValidationException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    /**
     * Constructeur avec code d'erreur, cause et arguments.
     *
     * @param errorCode le code d'erreur spécifique
     * @param cause la cause de l'exception
     * @param arguments les arguments pour formater le message
     */
    public ValidationException(ErrorCode errorCode, Throwable cause, Object... arguments) {
        super(errorCode, cause, arguments);
    }
}