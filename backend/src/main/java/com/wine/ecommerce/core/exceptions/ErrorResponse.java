package com.wine.ecommerce.core.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Structure standardisée pour les réponses d'erreur de l'API.
 * 
 * Cette classe définit le format uniforme de retour d'erreur pour toutes
 * les endpoints de l'application, facilitant la gestion côté client.
 */
@Schema(description = "Réponse d'erreur standardisée de l'API")
public class ErrorResponse {

    @Schema(description = "Code d'erreur unique", example = "USER_001")
    private String errorCode;

    @Schema(description = "Message d'erreur principal", example = "Utilisateur non trouvé")
    private String message;

    @Schema(description = "Description détaillée de l'erreur", 
            example = "L'utilisateur avec l'ID 123 n'existe pas dans le système")
    private String details;

    @Schema(description = "Timestamp de l'erreur", example = "2023-10-06T14:30:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;

    @Schema(description = "Chemin de l'endpoint qui a généré l'erreur", example = "/api/users/123")
    private String path;

    @Schema(description = "Méthode HTTP utilisée", example = "GET")
    private String method;

    @Schema(description = "Code de statut HTTP", example = "404")
    private int status;

    @Schema(description = "Liste des erreurs de validation (optionnel)")
    private List<ValidationError> validationErrors;

    /**
     * Constructeur par défaut.
     */
    public ErrorResponse() {
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Constructeur principal.
     *
     * @param errorCode le code d'erreur unique
     * @param message le message d'erreur principal
     * @param details la description détaillée
     * @param path le chemin de l'endpoint
     * @param method la méthode HTTP
     * @param status le code de statut HTTP
     */
    public ErrorResponse(String errorCode, String message, String details, 
                        String path, String method, int status) {
        this();
        this.errorCode = errorCode;
        this.message = message;
        this.details = details;
        this.path = path;
        this.method = method;
        this.status = status;
    }

    // Getters et Setters
    public String getErrorCode() { return errorCode; }
    public void setErrorCode(String errorCode) { this.errorCode = errorCode; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }

    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public List<ValidationError> getValidationErrors() { return validationErrors; }
    public void setValidationErrors(List<ValidationError> validationErrors) { 
        this.validationErrors = validationErrors; 
    }

    /**
     * Classe interne pour les erreurs de validation.
     */
    @Schema(description = "Erreur de validation spécifique")
    public static class ValidationError {
        
        @Schema(description = "Nom du champ en erreur", example = "email")
        private String field;
        
        @Schema(description = "Valeur rejetée", example = "invalid-email")
        private Object rejectedValue;
        
        @Schema(description = "Message d'erreur de validation", 
                example = "L'email doit avoir un format valide")
        private String message;

        public ValidationError() {}

        public ValidationError(String field, Object rejectedValue, String message) {
            this.field = field;
            this.rejectedValue = rejectedValue;
            this.message = message;
        }

        // Getters et Setters
        public String getField() { return field; }
        public void setField(String field) { this.field = field; }

        public Object getRejectedValue() { return rejectedValue; }
        public void setRejectedValue(Object rejectedValue) { this.rejectedValue = rejectedValue; }

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
}