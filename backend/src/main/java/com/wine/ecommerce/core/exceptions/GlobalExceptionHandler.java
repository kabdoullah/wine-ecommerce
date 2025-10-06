package com.wine.ecommerce.core.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import jakarta.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Gestionnaire global des exceptions pour l'application.
 * 
 * Cette classe intercepte toutes les exceptions levées par les contrôleurs
 * et les transforme en réponses d'erreur standardisées.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Gère les exceptions métier personnalisées.
     */
    @ExceptionHandler(WineEcommerceException.class)
    public ResponseEntity<ErrorResponse> handleWineEcommerceException(
            WineEcommerceException ex, HttpServletRequest request) {
        
        logger.warn("Exception métier: {} - {}", ex.getErrorCode().getCode(), ex.getMessage());

        ErrorResponse errorResponse = createErrorResponse(
            ex.getErrorCode().getCode(),
            ex.getMessage(),
            ex.getMessage(),
            request.getRequestURI(),
            request.getMethod(),
            ex.getErrorCode().getHttpStatus().value()
        );

        return ResponseEntity
            .status(ex.getErrorCode().getHttpStatus())
            .body(errorResponse);
    }

    /**
     * Gère les erreurs de validation Spring.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        
        logger.warn("Erreur de validation: {}", ex.getMessage());

        List<ErrorResponse.ValidationError> validationErrors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(this::createValidationError)
            .collect(Collectors.toList());

        ErrorResponse errorResponse = createErrorResponse(
            "VALIDATION_ERROR",
            "Erreurs de validation des données",
            "Les données fournies ne respectent pas les contraintes de validation",
            request.getRequestURI(),
            request.getMethod(),
            HttpStatus.BAD_REQUEST.value()
        );
        
        errorResponse.setValidationErrors(validationErrors);

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(errorResponse);
    }

    /**
     * Gère les erreurs d'authentification Spring Security.
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(
            AuthenticationException ex, HttpServletRequest request) {
        
        logger.warn("Erreur d'authentification: {}", ex.getMessage());

        String errorCode = "AUTH_001";
        String message = "Erreur d'authentification";
        
        if (ex instanceof BadCredentialsException) {
            errorCode = "AUTH_002";
            message = "Identifiants invalides";
        }

        ErrorResponse errorResponse = createErrorResponse(
            errorCode,
            message,
            ex.getMessage(),
            request.getRequestURI(),
            request.getMethod(),
            HttpStatus.UNAUTHORIZED.value()
        );

        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(errorResponse);
    }

    /**
     * Gère les erreurs d'autorisation Spring Security.
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(
            AccessDeniedException ex, HttpServletRequest request) {
        
        logger.warn("Accès refusé: {}", ex.getMessage());

        ErrorResponse errorResponse = createErrorResponse(
            "AUTH_003",
            "Accès refusé",
            "Vous n'avez pas les permissions nécessaires pour effectuer cette opération",
            request.getRequestURI(),
            request.getMethod(),
            HttpStatus.FORBIDDEN.value()
        );

        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(errorResponse);
    }

    /**
     * Gère toutes les autres exceptions non prévues.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex, HttpServletRequest request) {
        
        logger.error("Erreur interne du serveur", ex);

        ErrorResponse errorResponse = createErrorResponse(
            "INTERNAL_001",
            "Erreur interne du serveur",
            "Une erreur inattendue s'est produite. Veuillez réessayer plus tard.",
            request.getRequestURI(),
            request.getMethod(),
            HttpStatus.INTERNAL_SERVER_ERROR.value()
        );

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(errorResponse);
    }

    /**
     * Crée une réponse d'erreur standardisée.
     */
    private ErrorResponse createErrorResponse(String errorCode, String message, String details,
                                            String path, String method, int status) {
        return new ErrorResponse(errorCode, message, details, path, method, status);
    }

    /**
     * Crée une erreur de validation à partir d'une FieldError.
     */
    private ErrorResponse.ValidationError createValidationError(FieldError fieldError) {
        return new ErrorResponse.ValidationError(
            fieldError.getField(),
            fieldError.getRejectedValue(),
            fieldError.getDefaultMessage()
        );
    }
}