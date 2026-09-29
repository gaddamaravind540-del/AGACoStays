package com.agacostays.auth.controller;

import com.agacostays.auth.dto.request.*;
import com.agacostays.auth.dto.response.*;
import com.agacostays.auth.entity.User;
import com.agacostays.auth.exception.InvalidTokenException;
import com.agacostays.auth.mapper.TokenMapper;
import com.agacostays.auth.mapper.UserAuthMapper;
import com.agacostays.auth.service.AuthService;
import com.agacostays.auth.service.JwtService;
import com.agacostays.auth.service.RefreshTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/auth")
public class TokenController {

    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;
    private final TokenMapper tokenMapper;
    private final UserAuthMapper userAuthMapper;
    private final AuthService authService;

    public TokenController(
            RefreshTokenService refreshTokenService,
            JwtService jwtService,
            TokenMapper tokenMapper,
            UserAuthMapper userAuthMapper,
            AuthService authService) {
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
        this.tokenMapper = tokenMapper;
        this.userAuthMapper = userAuthMapper;
        this.authService = authService;
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<TokenResponse>> refreshToken(
            @Valid @RequestBody RefreshTokenRequest request,
            HttpServletRequest servletRequest) {

        User user = refreshTokenService.validateAndGetUser(request.refreshToken());
        String accessToken = jwtService.generateAccessToken(user);

        TokenResponse response = tokenMapper.map(
                accessToken,
                request.refreshToken(),
                jwtService.getAccessTokenExpirationSeconds(),
                Instant.now()
        );

        return ResponseEntity.ok(ApiResponse.success(
                "Access token refreshed",
                response,
                traceId(servletRequest)
        ));
    }

    @PostMapping("/validate-token")
    public ResponseEntity<ApiResponse<UserAuthResponse>> validateToken(
            @Valid @RequestBody ValidateTokenRequest request,
            HttpServletRequest servletRequest) {
        return ResponseEntity.ok(ApiResponse.success(
                "Token is valid",
                authService.validateToken(request),
                traceId(servletRequest)
        ));
    }

    private String traceId(HttpServletRequest request) {
        String value = request.getHeader("X-Trace-Id");
        return value == null ? "unknown" : value;
    }
}
