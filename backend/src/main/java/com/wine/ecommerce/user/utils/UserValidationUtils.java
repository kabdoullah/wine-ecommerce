package com.wine.ecommerce.user.utils;

import com.wine.ecommerce.user.constants.UserConstants;
import com.wine.ecommerce.user.entities.User;
import com.wine.ecommerce.user.enums.UserRole;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

public final class UserValidationUtils {
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$"
    );
    
    private static final Pattern PHONE_PATTERN = Pattern.compile(
        "^[+]?[0-9]{10,15}$"
    );

    private UserValidationUtils() {
        // Utility class
    }

    public static boolean isValidEmail(String email) {
        return StringUtils.hasText(email) && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isValidPhone(String phone) {
        return !StringUtils.hasText(phone) || PHONE_PATTERN.matcher(phone).matches();
    }

    public static boolean isValidPassword(String password) {
        return StringUtils.hasText(password) 
                && password.length() >= UserConstants.Validation.MIN_PASSWORD_LENGTH
                && password.length() <= UserConstants.Validation.MAX_PASSWORD_LENGTH;
    }

    public static boolean isValidName(String name) {
        return StringUtils.hasText(name) 
                && name.trim().length() <= UserConstants.Validation.MAX_NAME_LENGTH;
    }

    public static boolean canDeleteUser(User user, long superAdminCount) {
        if (user.hasRole(UserRole.SUPER_ADMIN) && superAdminCount <= 1) {
            return false;
        }
        return true;
    }

    public static boolean canRemoveRole(User user) {
        return user.getRoles().size() > 1;
    }
}