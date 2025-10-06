package com.wine.ecommerce.user.exceptions;

import com.wine.ecommerce.core.exceptions.ConflictException;
import com.wine.ecommerce.core.exceptions.ErrorCode;

/**
 * Exception levée lorsqu'un nom d'utilisateur est déjà utilisé.
 * 
 * Cette exception est utilisée lors de la création ou modification d'un utilisateur
 * lorsque le nom d'utilisateur fourni est déjà pris par un autre compte.
 */
public class UsernameAlreadyExistsException extends ConflictException {

    /**
     * Constructeur avec le nom d'utilisateur en conflit.
     *
     * @param username le nom d'utilisateur déjà utilisé
     */
    public UsernameAlreadyExistsException(String username) {
        super(ErrorCode.USER_USERNAME_ALREADY_EXISTS, username);
    }
}