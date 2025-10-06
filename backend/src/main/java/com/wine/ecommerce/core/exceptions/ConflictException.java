package com.wine.ecommerce.core.exceptions;

/**
 * Exception levée en cas de conflit de données.
 * 
 * Cette exception est utilisée lorsque l'opération demandée ne peut pas
 * être exécutée en raison d'un conflit avec l'état actuel des données
 * (ex: email déjà utilisé, ressource déjà existante, etc.).
 */
public class ConflictException extends WineEcommerceException {

    /**
     * Constructeur avec code d'erreur.
     *
     * @param errorCode le code d'erreur spécifique
     */
    public ConflictException(ErrorCode errorCode) {
        super(errorCode);
    }

    /**
     * Constructeur avec code d'erreur et arguments.
     *
     * @param errorCode le code d'erreur spécifique
     * @param arguments les arguments pour formater le message
     */
    public ConflictException(ErrorCode errorCode, Object... arguments) {
        super(errorCode, arguments);
    }

    /**
     * Constructeur avec code d'erreur et cause.
     *
     * @param errorCode le code d'erreur spécifique
     * @param cause la cause de l'exception
     */
    public ConflictException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    /**
     * Constructeur avec code d'erreur, cause et arguments.
     *
     * @param errorCode le code d'erreur spécifique
     * @param cause la cause de l'exception
     * @param arguments les arguments pour formater le message
     */
    public ConflictException(ErrorCode errorCode, Throwable cause, Object... arguments) {
        super(errorCode, cause, arguments);
    }
}