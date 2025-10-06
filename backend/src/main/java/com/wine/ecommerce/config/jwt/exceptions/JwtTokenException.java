package com.wine.ecommerce.config.jwt.exceptions;

import com.wine.ecommerce.core.exceptions.ErrorCode;
import com.wine.ecommerce.core.exceptions.UnauthorizedException;

/**
 * Exception levée lors d'erreurs liées aux tokens JWT.
 * <p>
 * Cette exception est utilisée pour tous les problèmes de validation,
 * d'expiration ou de format des tokens JWT.
 */
public class JwtTokenException extends UnauthorizedException {

    /**
     * Constructeur pour token expiré.
     *
     * @return exception pour token expiré
     */
    public static JwtTokenException expired() {
        return new JwtTokenException(ErrorCode.JWT_TOKEN_EXPIRED);
    }

    /**
     * Constructeur pour token invalide.
     *
     * @return exception pour token invalide
     */
    public static JwtTokenException invalid() {
        return new JwtTokenException(ErrorCode.JWT_TOKEN_INVALID);
    }

    /**
     * Constructeur pour token malformé.
     *
     * @return exception pour token malformé
     */
    public static JwtTokenException malformed() {
        return new JwtTokenException(ErrorCode.JWT_TOKEN_MALFORMED);
    }

    /**
     * Constructeur pour signature invalide.
     *
     * @return exception pour signature invalide
     */
    public static JwtTokenException invalidSignature() {
        return new JwtTokenException(ErrorCode.JWT_INVALID_SIGNATURE);
    }

    /**
     * Constructeur pour token non supporté.
     *
     * @return exception pour token non supporté
     */
    public static JwtTokenException unsupported() {
        return new JwtTokenException(ErrorCode.JWT_TOKEN_UNSUPPORTED);
    }

    /**
     * Constructeur pour claims vides.
     *
     * @return exception pour claims vides
     */
    public static JwtTokenException emptyClaims() {
        return new JwtTokenException(ErrorCode.JWT_CLAIMS_EMPTY);
    }

    /**
     * Constructeur avec code d'erreur.
     *
     * @param errorCode le code d'erreur spécifique
     */
    private JwtTokenException(ErrorCode errorCode) {
        super(errorCode);
    }

    /**
     * Constructeur avec code d'erreur et cause.
     *
     * @param errorCode le code d'erreur spécifique
     * @param cause la cause de l'exception
     */
    public JwtTokenException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}