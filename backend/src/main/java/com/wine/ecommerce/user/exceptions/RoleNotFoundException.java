package com.wine.ecommerce.user.exceptions;

import com.wine.ecommerce.core.exceptions.ErrorCode;
import com.wine.ecommerce.core.exceptions.ResourceNotFoundException;

/**
 * Exception levée lorsqu'un rôle demandé n'est pas trouvé.
 * 
 * Cette exception est utilisée lors de l'attribution de rôles ou
 * de la recherche de rôles spécifiques dans le système.
 */
public class RoleNotFoundException extends ResourceNotFoundException {

    /**
     * Constructeur avec ID du rôle.
     *
     * @param roleId l'ID du rôle non trouvé
     */
    public RoleNotFoundException(Long roleId) {
        super(ErrorCode.ROLE_NOT_FOUND, roleId);
    }

    /**
     * Constructeur avec nom du rôle.
     *
     * @param roleName le nom du rôle non trouvé
     */
    public RoleNotFoundException(String roleName) {
        super(ErrorCode.ROLE_NOT_FOUND, roleName);
    }

    /**
     * Constructeur pour un rôle non trouvé par nom avec factory method.
     *
     * @param roleName le nom du rôle non trouvé
     * @return nouvelle instance de RoleNotFoundException
     */
    public static RoleNotFoundException byName(String roleName) {
        return new RoleNotFoundException(roleName);
    }
}