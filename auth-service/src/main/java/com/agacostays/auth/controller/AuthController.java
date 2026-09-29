package com.agacostays.auth.controller;

import com.agacostays.auth.dto.request.*;
import com.agacostays.auth.dto.response.*;
import com.agacostays.auth.mapper.OtpMapper;
import com.agacostays.auth.security.CurrentUserResolver;
import com.agacostays.auth.service.AuthService;
import com.agacostays.auth.service.OtpService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final OtpService otpService;
    private final CurrentUserResolver currentUserResolver;

    public AuthController(
            AuthService authService,
            OtpService otpService,
            CurrentUserResolver currentUserResolver) {
        this.authService = authService;
        this.otpService = otpService;
        this.currentUserResolver = currentUserResolver;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(
            @Valid @RequestBody RegisterRequest request,
            HttpServletRequest servletRequest) {
        return ResponseEntity.ok(ApiResponse.success(
                "Customer registered successfully",
                authService.register(request),
                traceId(servletRequest)
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletRequest servletRequest) {
        return ResponseEntity.ok(ApiResponse.success(
                "Login successful",
                authService.login(request, servletRequest.getRemoteAddr(), servletRequest.getHeader("User-Agent")),
                traceId(servletRequest)
        ));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(
            @Valid @RequestBody LogoutRequest request,
            HttpServletRequest servletRequest) {
        authService.logout(request, currentUserResolver.userId(servletRequest));
        return ResponseEntity.ok(ApiResponse.success(
                "Logout successful",
                null,
                traceId(servletRequest)
        ));
    }

    @PostMapping("/otp/send")
    public ResponseEntity<ApiResponse<OtpResponse>> sendOtp(
            @Valid @RequestBody SendOtpRequest request,
            HttpServletRequest servletRequest) {
        return ResponseEntity.ok(ApiResponse.success(
                "OTP generated",
                otpService.send(request),
                traceId(servletRequest)
        ));
    }

    @PostMapping("/otp/verify")
    public ResponseEntity<ApiResponse<Void>> verifyOtp(
            @Valid @RequestBody VerifyOtpRequest request,
            HttpServletRequest servletRequest) {
        otpService.verify(request);
        return ResponseEntity.ok(ApiResponse.success(
                "OTP verified successfully",
                null,
                traceId(servletRequest)
        ));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserAuthResponse>> me(HttpServletRequest servletRequest) {
        return ResponseEntity.ok(ApiResponse.success(
                "Current authenticated identity",
                authService.currentUser(currentUserResolver.userId(servletRequest)),
                traceId(servletRequest)
        ));
    }

    private String traceId(HttpServletRequest request) {
        String value = request.getHeader("X-Trace-Id");
        return value == null ? "unknown" : value;
    }
}
