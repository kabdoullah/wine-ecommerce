package com.wine.ecommerce.core.exceptions;

/**
 * Exception racine pour toutes les exceptions métier de l'application Wine E-commerce.
 * 
 * Cette classe sert de base pour toutes les exceptions personnalisées du domaine métier.
 * Elle permet un traitement uniforme des erreurs et facilite la gestion centralisée.
 */
public abstract class WineEcommerceException extends RuntimeException {

    private final ErrorCode errorCode;
    private final Object[] arguments;

    /**
     * Constructeur avec code d'erreur.
     *
     * @param errorCode le code d'erreur spécifique
     */
    protected WineEcommerceException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.arguments = new Object[0];
    }

    /**
     * Constructeur avec code d'erreur et arguments pour le message.
     *
     * @param errorCode le code d'erreur spécifique
     * @param arguments les arguments pour formater le message
     */
    protected WineEcommerceException(ErrorCode errorCode, Object... arguments) {
        super(String.format(errorCode.getMessage(), arguments));
        this.errorCode = errorCode;
        this.arguments = arguments;
    }

    /**
     * Constructeur avec code d'erreur et cause.
     *
     * @param errorCode le code d'erreur spécifique
     * @param cause la cause de l'exception
     */
    protected WineEcommerceException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
        this.arguments = new Object[0];
    }

    /**
     * Constructeur avec code d'erreur, arguments et cause.
     *
     * @param errorCode le code d'erreur spécifique
     * @param cause la cause de l'exception
     * @param arguments les arguments pour formater le message
     */
    protected WineEcommerceException(ErrorCode errorCode, Throwable cause, Object... arguments) {
        super(String.format(errorCode.getMessage(), arguments), cause);
        this.errorCode = errorCode;
        this.arguments = arguments;
    }

    /**
     * Retourne le code d'erreur associé à cette exception.
     *
     * @return le code d'erreur
     */
    public ErrorCode getErrorCode() {
        return errorCode;
    }

    /**
     * Retourne les arguments utilisés pour formater le message.
     *
     * @return les arguments du message
     */
    public Object[] getArguments() {
        return arguments.clone();
    }

    /**
     * Retourne le code d'erreur sous forme de chaîne.
     *
     * @return le code d'erreur comme string
     */
    public String getCode() {
        return errorCode.getCode();
    }
}