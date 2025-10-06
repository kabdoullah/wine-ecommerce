package com.wine.ecommerce.user.exceptions;

/**
 * Exception levée lors d'erreurs liées aux refresh tokens.
 */
public class RefreshTokenException extends RuntimeException {

    public RefreshTokenException(String message) {
        super(message);
    }

    public static RefreshTokenException tokenNotFound(String token) {
        return new RefreshTokenException("Refresh token introuvable: " + token);
    }

    public static RefreshTokenException tokenExpired(String token) {
        return new RefreshTokenException("Refresh token expiré: " + token);
    }
}