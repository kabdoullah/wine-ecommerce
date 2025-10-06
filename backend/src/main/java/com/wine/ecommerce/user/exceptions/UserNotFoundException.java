package com.wine.ecommerce.user.exceptions;

import com.wine.ecommerce.core.exceptions.ErrorCode;
import com.wine.ecommerce.core.exceptions.ResourceNotFoundException;

public class UserNotFoundException extends ResourceNotFoundException {

    public UserNotFoundException(java.util.UUID userId) {
        super(ErrorCode.USER_NOT_FOUND, userId);
    }

    public UserNotFoundException(String username) {
        super(ErrorCode.USER_NOT_FOUND_BY_USERNAME, username);
    }

    public static UserNotFoundException byEmail(String email) {
        return new UserNotFoundException(ErrorCode.USER_NOT_FOUND_BY_EMAIL, email);
    }

    private UserNotFoundException(ErrorCode errorCode, Object... arguments) {
        super(errorCode, arguments);
    }
}