package com.wine.ecommerce.user.controllers;

import com.wine.ecommerce.core.exceptions.ErrorResponse;
import com.wine.ecommerce.user.dto.*;
import com.wine.ecommerce.user.enums.UserRole;
import com.wine.ecommerce.user.enums.UserStatus;
import com.wine.ecommerce.user.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Gestion des utilisateurs", description = "API pour la gestion des utilisateurs du système")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@SecurityRequirement(name = "Bearer Authentication")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Créer un nouvel utilisateur", 
               description = "Crée un nouvel utilisateur dans le système. Réservé aux administrateurs.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Utilisateur créé avec succès",
                    content = @Content(schema = @Schema(implementation = UserResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "Données invalides ou email déjà utilisé",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "401", description = "Non authentifié",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "403", description = "Accès refusé - permissions insuffisantes",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<UserResponseDto> createUser(
            @Parameter(description = "Données du nouvel utilisateur", required = true)
            @Valid @RequestBody CreateUserRequest request) {
        UserResponseDto createdUser = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @Operation(summary = "Lister tous les utilisateurs", 
               description = "Récupère la liste paginée de tous les utilisateurs avec filtres optionnels")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des utilisateurs récupérée avec succès"),
        @ApiResponse(responseCode = "401", description = "Non authentifié",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "403", description = "Accès refusé - permissions insuffisantes",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<Page<UserSummaryDto>> getAllUsers(
            @Parameter(description = "Numéro de page (0-indexed)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Taille de la page", example = "10")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Champ de tri", example = "createdAt")
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @Parameter(description = "Direction du tri", example = "desc")
            @RequestParam(defaultValue = "desc") String sortDir,
            @Parameter(description = "Filtrer par statut utilisateur")
            @RequestParam(required = false) UserStatus status,
            @Parameter(description = "Filtrer par rôle utilisateur")
            @RequestParam(required = false) UserRole role
    ) {
        Sort sort = sortDir.equalsIgnoreCase("desc") 
            ? Sort.by(sortBy).descending() 
            : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<UserSummaryDto> users;
        if (status != null) {
            users = userService.getUsersByStatus(status, pageable);
        } else if (role != null) {
            users = userService.getUsersByRole(role, pageable);
        } else {
            users = userService.getAllUsers(pageable);
        }

        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Obtenir les détails d'un utilisateur", 
               description = "Récupère les informations détaillées d'un utilisateur spécifique")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Détails utilisateur récupérés avec succès",
                    content = @Content(schema = @Schema(implementation = UserResponseDto.class))),
        @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "401", description = "Non authentifié",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "403", description = "Accès refusé - permissions insuffisantes",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<UserResponseDto> getUserDetails(
            @Parameter(description = "ID de l'utilisateur", required = true, example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable UUID userId) {
        UserResponseDto user = userService.getUserDetails(userId);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Mettre à jour un utilisateur", 
               description = "Met à jour les informations d'un utilisateur existant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Utilisateur mis à jour avec succès",
                    content = @Content(schema = @Schema(implementation = UserResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "Données invalides",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "409", description = "Email déjà utilisé par un autre utilisateur",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<UserResponseDto> updateUser(
            @Parameter(description = "ID de l'utilisateur", required = true)
            @PathVariable UUID userId,
            @Parameter(description = "Nouvelles données de l'utilisateur", required = true)
            @Valid @RequestBody UpdateUserRequest request
    ) {
        UserResponseDto updatedUser = userService.updateUser(userId, request);
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "Assigner un rôle à un utilisateur", 
               description = "Ajoute un rôle à un utilisateur existant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rôle assigné avec succès"),
        @ApiResponse(responseCode = "400", description = "Rôle invalide ou déjà assigné",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/{userId}/roles/assign")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<MessageResponse> assignRole(
            @Parameter(description = "ID de l'utilisateur", required = true)
            @PathVariable UUID userId,
            @Parameter(description = "Rôle à assigner", required = true)
            @Valid @RequestBody AssignRoleRequest request
    ) {
        userService.assignRole(userId, request.role());
        return ResponseEntity.ok(new MessageResponse(com.wine.ecommerce.user.constants.UserConstants.Messages.ROLE_ASSIGNED_SUCCESS));
    }

    @Operation(summary = "Retirer un rôle d'un utilisateur", 
               description = "Supprime un rôle d'un utilisateur existant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rôle retiré avec succès"),
        @ApiResponse(responseCode = "400", description = "Rôle non assigné à cet utilisateur",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/{userId}/roles/remove")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<MessageResponse> removeRole(
            @Parameter(description = "ID de l'utilisateur", required = true)
            @PathVariable UUID userId,
            @Parameter(description = "Rôle à retirer", required = true)
            @Valid @RequestBody AssignRoleRequest request
    ) {
        userService.removeRole(userId, request.role());
        return ResponseEntity.ok(new MessageResponse(com.wine.ecommerce.user.constants.UserConstants.Messages.ROLE_REMOVED_SUCCESS));
    }

    @Operation(summary = "Changer le statut d'un utilisateur", 
               description = "Modifie le statut d'un utilisateur (ACTIVE, SUSPENDED, INACTIVE)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Statut modifié avec succès"),
        @ApiResponse(responseCode = "400", description = "Statut invalide",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PatchMapping("/{userId}/status")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<MessageResponse> changeUserStatus(
            @Parameter(description = "ID de l'utilisateur", required = true)
            @PathVariable UUID userId,
            @Parameter(description = "Nouveau statut de l'utilisateur", required = true)
            @RequestParam UserStatus status
    ) {
        userService.changeUserStatus(userId, status);
        return ResponseEntity.ok(new MessageResponse(com.wine.ecommerce.user.constants.UserConstants.Messages.USER_STATUS_CHANGED_SUCCESS));
    }

    @Operation(summary = "Supprimer un utilisateur", 
               description = "Supprime définitivement un utilisateur du système. Opération réservée aux super administrateurs.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Utilisateur supprimé avec succès"),
        @ApiResponse(responseCode = "400", description = "Impossible de supprimer le dernier super administrateur",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "403", description = "Accès refusé - permissions de super administrateur requises",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<MessageResponse> deleteUser(
            @Parameter(description = "ID de l'utilisateur à supprimer", required = true)
            @PathVariable UUID userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok(new MessageResponse(com.wine.ecommerce.user.constants.UserConstants.Messages.USER_DELETED_SUCCESS));
    }

}