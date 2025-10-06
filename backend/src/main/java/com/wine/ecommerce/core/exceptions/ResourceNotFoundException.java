package com.wine.ecommerce.core.exceptions;

/**
 * Exception levée lorsqu'une ressource demandée n'est pas trouvée.
 * 
 * Cette exception est typiquement utilisée pour les opérations de lecture
 * où une entité spécifique n'existe pas dans le système.
 */
public class ResourceNotFoundException extends WineEcommerceException {

    /**
     * Constructeur avec code d'erreur.
     *
     * @param errorCode le code d'erreur spécifique
     */
    public ResourceNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    /**
     * Constructeur avec code d'erreur et arguments.
     *
     * @param errorCode le code d'erreur spécifique
     * @param arguments les arguments pour formater le message (ex: ID de la ressource)
     */
    public ResourceNotFoundException(ErrorCode errorCode, Object... arguments) {
        super(errorCode, arguments);
    }

    /**
     * Constructeur avec code d'erreur et cause.
     *
     * @param errorCode le code d'erreur spécifique
     * @param cause la cause de l'exception
     */
    public ResourceNotFoundException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    /**
     * Constructeur avec code d'erreur, cause et arguments.
     *
     * @param errorCode le code d'erreur spécifique
     * @param cause la cause de l'exception
     * @param arguments les arguments pour formater le message
     */
    public ResourceNotFoundException(ErrorCode errorCode, Throwable cause, Object... arguments) {
        super(errorCode, cause, arguments);
    }
}