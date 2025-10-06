package com.wine.ecommerce.user.controllers;

import com.wine.ecommerce.config.jwt.JwtUtils;
import com.wine.ecommerce.core.exceptions.ErrorResponse;
import com.wine.ecommerce.user.dto.*;
import com.wine.ecommerce.user.entities.RefreshToken;
import com.wine.ecommerce.user.entities.*;
import com.wine.ecommerce.user.exceptions.EmailAlreadyExistsException;
import com.wine.ecommerce.user.exceptions.RefreshTokenException;
import com.wine.ecommerce.user.exceptions.RoleNotFoundException;
import com.wine.ecommerce.user.repositories.RoleRepository;
import com.wine.ecommerce.user.repositories.UserRepository;
import com.wine.ecommerce.user.services.RefreshTokenService;
import com.wine.ecommerce.user.services.UserRegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Authentification", description = "API pour l'authentification et l'inscription des utilisateurs")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final UserRegistrationService userRegistrationService;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;

    @Operation(summary = "Connexion utilisateur",
            description = "Authentifie un utilisateur et retourne un token JWT avec les informations utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Connexion réussie",
                    content = @Content(schema = @Schema(implementation = JwtResponse.class))),
            @ApiResponse(responseCode = "400", description = "Données invalides",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Identifiants incorrects",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(
            @Parameter(description = "Identifiants de connexion (email et mot de passe)", required = true)
            @Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.email(),
                        loginRequest.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();
        
        String jwt = jwtUtils.generateToken(authentication);
        
        // Créer le refresh token
        User user = userRepository.findByEmail(userDetails.getEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return ResponseEntity.ok(new JwtResponse(
                jwt,
                refreshToken.getToken(),
                "Bearer",
                userDetails.getId(),
                userDetails.getEmail(),
                userDetails.getFirstName(),
                userDetails.getLastName(),
                roles));
    }

    @Operation(summary = "Inscription utilisateur",
            description = "Crée un nouveau compte utilisateur avec le rôle CLIENT par défaut")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Inscription réussie",
                    content = @Content(schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "400", description = "Données invalides (validation échouée)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Email déjà utilisé par un autre compte",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erreur interne (rôle CLIENT non trouvé)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/register")
    public ResponseEntity<MessageResponse> registerUser(
            @Parameter(description = "Données d'inscription du nouvel utilisateur", required = true)
            @Valid @RequestBody SignupRequest signUpRequest) {

        userRegistrationService.registerUser(signUpRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new MessageResponse(com.wine.ecommerce.user.constants.UserConstants.Messages.USER_REGISTERED_SUCCESS));
    }

    @Operation(summary = "Rafraîchir le token d'accès",
            description = "Génère un nouveau token d'accès à partir d'un refresh token valide")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token rafraîchi avec succès",
                    content = @Content(schema = @Schema(implementation = JwtResponse.class))),
            @ApiResponse(responseCode = "401", description = "Refresh token invalide ou expiré",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Requête invalide",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refreshToken(
            @Parameter(description = "Refresh token pour générer un nouveau token d'accès", required = true)
            @Valid @RequestBody RefreshTokenRequest request) {

        String refreshTokenValue = request.refreshToken();
        
        RefreshToken refreshToken = refreshTokenService.findByToken(refreshTokenValue)
                .orElseThrow(() -> RefreshTokenException.tokenNotFound(refreshTokenValue));

        refreshToken = refreshTokenService.verifyExpiration(refreshToken);
        
        User user = refreshToken.getUser();
        String newAccessToken = jwtUtils.generateTokenFromUsername(user.getEmail());
        
        // Créer un nouveau refresh token
        RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(user);

        List<String> roles = user.getRoles().stream()
                .map(role -> role.getName().name())
                .toList();

        return ResponseEntity.ok(new JwtResponse(
                newAccessToken,
                newRefreshToken.getToken(),
                "Bearer",
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                roles));
    }

    @Operation(summary = "Déconnexion utilisateur",
            description = "Révoque le refresh token de l'utilisateur connecté")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Déconnexion réussie"),
            @ApiResponse(responseCode = "401", description = "Refresh token invalide",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/logout")
    public ResponseEntity<MessageResponse> logout(
            @Parameter(description = "Refresh token à révoquer", required = true)
            @Valid @RequestBody RefreshTokenRequest request) {

        String refreshTokenValue = request.refreshToken();
        
        RefreshToken refreshToken = refreshTokenService.findByToken(refreshTokenValue)
                .orElseThrow(() -> RefreshTokenException.tokenNotFound(refreshTokenValue));

        refreshTokenService.deleteByUser(refreshToken.getUser());

        return ResponseEntity.ok(new MessageResponse(com.wine.ecommerce.user.constants.UserConstants.Messages.LOGOUT_SUCCESS));
    }
}