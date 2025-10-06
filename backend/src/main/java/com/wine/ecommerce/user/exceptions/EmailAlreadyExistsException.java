package com.wine.ecommerce.user.exceptions;

import com.wine.ecommerce.core.exceptions.ConflictException;
import com.wine.ecommerce.core.exceptions.ErrorCode;

/**
 * Exception levée lorsqu'un email est déjà utilisé par un autre utilisateur.
 * <p>
 * Cette exception est utilisée lors de la création ou modification d'un utilisateur
 * lorsque l'email fourni est déjà associé à un autre compte.
 */
public class EmailAlreadyExistsException extends ConflictException {

    /**
     * Constructeur avec l'email en conflit.
     *
     * @param email l'email déjà utilisé
     */
    public EmailAlreadyExistsException(String email) {
        super(ErrorCode.USER_EMAIL_ALREADY_EXISTS, email);
    }
}