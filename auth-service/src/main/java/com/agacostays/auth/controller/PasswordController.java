package com.agacostays.auth.controller;

import com.agacostays.auth.dto.request.*;
import com.agacostays.auth.dto.response.ApiResponse;
import com.agacostays.auth.dto.response.PasswordResetResponse;
import com.agacostays.auth.security.CurrentUserResolver;
import com.agacostays.auth.service.PasswordService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class PasswordController {

    private final PasswordService passwordService;
    private final CurrentUserResolver currentUserResolver;

    public PasswordController(PasswordService passwordService, CurrentUserResolver currentUserResolver) {
        this.passwordService = passwordService;
        this.currentUserResolver = currentUserResolver;
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<PasswordResetResponse>> forgotPassword(
            @Valid @RequestBody ForgotPasswordRequest request,
            HttpServletRequest servletRequest) {
        return ResponseEntity.ok(ApiResponse.success(
                "Password reset initiated",
                passwordService.forgotPassword(request),
                traceId(servletRequest)
        ));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<Void>> resetPassword(
            @Valid @RequestBody ResetPasswordRequest request,
            HttpServletRequest servletRequest) {
        passwordService.resetPassword(request);
        return ResponseEntity.ok(ApiResponse.success(
                "Password reset successfully",
                null,
                traceId(servletRequest)
        ));
    }

    @PutMapping("/change-password")
    public ResponseEntity<ApiResponse<Void>> changePassword(
            @Valid @RequestBody ChangePasswordRequest request,
            HttpServletRequest servletRequest) {
        passwordService.changePassword(
                currentUserResolver.userId(servletRequest),
                request
        );

        return ResponseEntity.ok(ApiResponse.success(
                "Password changed successfully",
                null,
                traceId(servletRequest)
        ));
    }

    private String traceId(HttpServletRequest request) {
        String value = request.getHeader("X-Trace-Id");
        return value == null ? "unknown" : value;
    }
}
