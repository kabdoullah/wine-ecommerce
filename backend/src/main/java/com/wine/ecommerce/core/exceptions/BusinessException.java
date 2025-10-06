package com.wine.ecommerce.core.exceptions;

/**
 * Exception pour les erreurs de logique métier.
 * 
 * Cette exception est levée lorsque des règles métier sont violées
 * ou lorsque des conditions d'affaires ne sont pas respectées.
 */
public class BusinessException extends WineEcommerceException {

    /**
     * Constructeur avec code d'erreur.
     *
     * @param errorCode le code d'erreur spécifique
     */
    public BusinessException(ErrorCode errorCode) {
        super(errorCode);
    }

    /**
     * Constructeur avec code d'erreur et arguments.
     *
     * @param errorCode le code d'erreur spécifique
     * @param arguments les arguments pour formater le message
     */
    public BusinessException(ErrorCode errorCode, Object... arguments) {
        super(errorCode, arguments);
    }

    /**
     * Constructeur avec code d'erreur et cause.
     *
     * @param errorCode le code d'erreur spécifique
     * @param cause la cause de l'exception
     */
    public BusinessException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    /**
     * Constructeur avec code d'erreur, cause et arguments.
     *
     * @param errorCode le code d'erreur spécifique
     * @param cause la cause de l'exception
     * @param arguments les arguments pour formater le message
     */
    public BusinessException(ErrorCode errorCode, Throwable cause, Object... arguments) {
        super(errorCode, cause, arguments);
    }
}