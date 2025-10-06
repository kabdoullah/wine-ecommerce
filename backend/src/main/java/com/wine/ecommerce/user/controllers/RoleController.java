package com.wine.ecommerce.user.controllers;

import com.wine.ecommerce.core.exceptions.ErrorResponse;
import com.wine.ecommerce.user.dto.UserRoleDto;
import com.wine.ecommerce.user.mappers.RoleMapper;
import com.wine.ecommerce.user.services.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Gestion des rôles", description = "API pour la gestion des rôles du système")
@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@SecurityRequirement(name = "Bearer Authentication")
public class RoleController {

    private final RoleService roleService;
    private final RoleMapper roleMapper;

    @Operation(summary = "Lister tous les rôles", 
               description = "Récupère la liste complète de tous les rôles du système")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des rôles récupérée avec succès"),
        @ApiResponse(responseCode = "401", description = "Non authentifié",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "403", description = "Accès refusé - permissions insuffisantes",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<List<UserRoleDto>> getAllRoles() {
        List<UserRoleDto> roles = roleMapper.toDtoList(roleService.getAllRoles());
        return ResponseEntity.ok(roles);
    }

    @Operation(summary = "Lister les rôles actifs", 
               description = "Récupère la liste des rôles actifs uniquement")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des rôles actifs récupérée avec succès"),
        @ApiResponse(responseCode = "401", description = "Non authentifié",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "403", description = "Accès refusé - permissions insuffisantes",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/active")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<List<UserRoleDto>> getActiveRoles() {
        List<UserRoleDto> activeRoles = roleMapper.toDtoList(roleService.getAllActiveRoles());
        return ResponseEntity.ok(activeRoles);
    }
}