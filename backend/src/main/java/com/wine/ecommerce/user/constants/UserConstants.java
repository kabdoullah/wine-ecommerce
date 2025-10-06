package com.wine.ecommerce.user.constants;

public final class UserConstants {
    
    private UserConstants() {
        // Utility class
    }
    
    public static final class Messages {
        public static final String USER_CREATED_SUCCESS = "User created successfully";
        public static final String USER_UPDATED_SUCCESS = "User updated successfully";
        public static final String USER_DELETED_SUCCESS = "User deleted successfully";
        public static final String USER_STATUS_CHANGED_SUCCESS = "User status changed successfully";
        public static final String ROLE_ASSIGNED_SUCCESS = "Role assigned successfully";
        public static final String ROLE_REMOVED_SUCCESS = "Role removed successfully";
        public static final String USER_REGISTERED_SUCCESS = "User registered successfully";
        public static final String LOGOUT_SUCCESS = "Logout successful";
        
        public static final String EMAIL_ALREADY_EXISTS = "A user with this email already exists";
        public static final String ROLE_NOT_FOUND = "Role not found: %s";
        public static final String USER_MUST_HAVE_AT_LEAST_ONE_ROLE = "User must have at least one role";
        public static final String CANNOT_DELETE_LAST_SUPER_ADMIN = "Cannot delete the last super administrator";
        public static final String USER_NOT_FOUND_WITH_ID = "User not found with ID: %s";
    }
    
    public static final class Validation {
        public static final int MIN_PASSWORD_LENGTH = 6;
        public static final int MAX_PASSWORD_LENGTH = 120;
        public static final int MAX_EMAIL_LENGTH = 100;
        public static final int MAX_NAME_LENGTH = 50;
        public static final int MAX_PHONE_LENGTH = 20;
    }
}