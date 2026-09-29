package com.agacostays.auth.controller;

import com.agacostays.auth.constants.AuthConstants;
import com.agacostays.auth.dto.request.LoginRequest;
import com.agacostays.auth.dto.request.LogoutRequest;
import com.agacostays.auth.dto.response.ApiResponse;
import com.agacostays.auth.dto.response.AuthResponse;
import com.agacostays.auth.entity.User;
import com.agacostays.auth.exception.InvalidCredentialsException;
import com.agacostays.auth.repository.UserRepository;
import com.agacostays.auth.security.CurrentUserResolver;
import com.agacostays.auth.service.AuthService;
import com.agacostays.auth.service.RefreshTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/root-admin/auth")
public class RootAdminAuthController {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;
    private final CurrentUserResolver currentUserResolver;

    public RootAdminAuthController(
            AuthService authService,
            UserRepository userRepository,
            RefreshTokenService refreshTokenService,
            CurrentUserResolver currentUserResolver) {
        this.authService = authService;
        this.userRepository = userRepository;
        this.refreshTokenService = refreshTokenService;
        this.currentUserResolver = currentUserResolver;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletRequest servletRequest) {

        User user = userRepository.findByEmailIgnoreCase(request.email().trim().toLowerCase())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid root admin credentials"));

        if (!AuthConstants.ROOT_ADMIN_ROLE.equalsIgnoreCase(user.getRole().getRoleName())) {
            throw new InvalidCredentialsException("The account is not a ROOT_ADMIN account");
        }

        AuthResponse response = authService.login(
                request,
                servletRequest.getRemoteAddr(),
                servletRequest.getHeader("User-Agent")
        );

        return ResponseEntity.ok(ApiResponse.success(
                "Root Admin login successful",
                response,
                traceId(servletRequest)
        ));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(
            @Valid @RequestBody LogoutRequest request,
            HttpServletRequest servletRequest) {

        Long userId = currentUserResolver.userId(servletRequest);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidCredentialsException("Authenticated root admin not found"));

        if (!AuthConstants.ROOT_ADMIN_ROLE.equalsIgnoreCase(user.getRole().getRoleName())) {
            throw new InvalidCredentialsException("Authenticated user is not a ROOT_ADMIN");
        }

        refreshTokenService.revoke(request.refreshToken());

        return ResponseEntity.ok(ApiResponse.success(
                "Root Admin logout successful",
                null,
                traceId(servletRequest)
        ));
    }

    private String traceId(HttpServletRequest request) {
        String value = request.getHeader("X-Trace-Id");
        return value == null ? "unknown" : value;
    }
}
