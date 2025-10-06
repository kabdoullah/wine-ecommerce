package com.wine.ecommerce.user.exceptions;

import com.wine.ecommerce.core.exceptions.BusinessException;
import com.wine.ecommerce.core.exceptions.ErrorCode;

/**
 * Exception levée lorsqu'une opération est tentée sur un utilisateur
 * avec un statut invalide pour cette opération.
 * 
 * Par exemple, essayer de connecter un utilisateur suspendu ou désactivé.
 */
public class InvalidUserStatusException extends BusinessException {

    /**
     * Constructeur avec le statut actuel de l'utilisateur.
     *
     * @param currentStatus le statut actuel de l'utilisateur
     */
    public InvalidUserStatusException(String currentStatus) {
        super(ErrorCode.USER_INVALID_STATUS, currentStatus);
    }

    /**
     * Constructeur avec le statut actuel et l'opération tentée.
     *
     * @param currentStatus le statut actuel de l'utilisateur
     * @param operation l'opération qui a été tentée
     */
    public InvalidUserStatusException(String currentStatus, String operation) {
        super(ErrorCode.USER_INVALID_STATUS_FOR_OPERATION, currentStatus, operation);
    }
}