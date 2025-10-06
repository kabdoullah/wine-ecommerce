package com.wine.ecommerce.user.exceptions;

import com.wine.ecommerce.core.exceptions.BusinessException;
import com.wine.ecommerce.core.exceptions.ErrorCode;

public class InsufficientRoleException extends BusinessException {
    
    public InsufficientRoleException(String operation) {
        super(ErrorCode.USER_INSUFFICIENT_PRIVILEGES, operation);
    }
}