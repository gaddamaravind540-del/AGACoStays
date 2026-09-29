package com.agacostays.auth.service.impl;

import com.agacostays.auth.constants.AuthConstants;
import com.agacostays.auth.dto.request.*;
import com.agacostays.auth.dto.response.AuthResponse;
import com.agacostays.auth.dto.response.TokenResponse;
import com.agacostays.auth.dto.response.UserAuthResponse;
import com.agacostays.auth.entity.Role;
import com.agacostays.auth.entity.User;
import com.agacostays.auth.enums.*;
import com.agacostays.auth.exception.*;
import com.agacostays.auth.mapper.TokenMapper;
import com.agacostays.auth.mapper.UserAuthMapper;
import com.agacostays.auth.repository.LoginAuditRepository;
import com.agacostays.auth.repository.RoleRepository;
import com.agacostays.auth.repository.UserRepository;
import com.agacostays.auth.security.JwtClaims;
import com.agacostays.auth.service.*;
import com.agacostays.auth.audit.LoginAudit;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final LoginAuditRepository loginAuditRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final UserAuthMapper userAuthMapper;
    private final TokenMapper tokenMapper;

    public AuthServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            LoginAuditRepository loginAuditRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            RefreshTokenService refreshTokenService,
            UserAuthMapper userAuthMapper,
            TokenMapper tokenMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.loginAuditRepository = loginAuditRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.userAuthMapper = userAuthMapper;
        this.tokenMapper = tokenMapper;
    }

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        String email = request.email().trim().toLowerCase();

        if (userRepository.existsByEmailIgnoreCase(email)) {
            throw new UserAlreadyExistsException("A user with this email already exists");
        }

        Role customerRole = roleRepository.findByRoleNameIgnoreCase(AuthConstants.CUSTOMER_ROLE)
                .orElseThrow(() -> new UserNotFoundException("CUSTOMER role is not configured"));

        User user = User.builder()
                .fullName(request.fullName().trim())
                .email(email)
                .phone(request.phone() == null ? null : request.phone().trim())
                .passwordHash(passwordEncoder.encode(request.password()))
                .role(customerRole)
                .userType(UserType.CUSTOMER)
                .status(UserStatus.ACTIVE)
                .authProvider(AuthProvider.LOCAL)
                .build();

        user = userRepository.save(user);

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = refreshTokenService.create(user);
        TokenResponse tokens = tokenMapper.map(
                accessToken,
                refreshToken,
                jwtService.getAccessTokenExpirationSeconds(),
                Instant.now()
        );

        return new AuthResponse(userAuthMapper.toResponse(user), tokens);
    }

    @Override
    @Transactional
    public AuthResponse login(LoginRequest request, String ipAddress, String userAgent) {
        String email = request.email().trim().toLowerCase();

        User user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> {
                    saveAudit(null, email, LoginStatus.FAILED, ipAddress, userAgent);
                    return new InvalidCredentialsException("Invalid email or password");
                });

        if (user.getStatus() == UserStatus.LOCKED) {
            saveAudit(user.getUserId(), email, LoginStatus.LOCKED, ipAddress, userAgent);
            throw new AccountLockedException("Account is locked");
        }

        if (user.getStatus() != UserStatus.ACTIVE) {
            saveAudit(user.getUserId(), email, LoginStatus.FAILED, ipAddress, userAgent);
            throw new InvalidCredentialsException("User account is not active");
        }

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            saveAudit(user.getUserId(), email, LoginStatus.FAILED, ipAddress, userAgent);
            throw new InvalidCredentialsException("Invalid email or password");
        }

        saveAudit(user.getUserId(), email, LoginStatus.SUCCESS, ipAddress, userAgent);

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = refreshTokenService.create(user);

        return new AuthResponse(
                userAuthMapper.toResponse(user),
                tokenMapper.map(
                        accessToken,
                        refreshToken,
                        jwtService.getAccessTokenExpirationSeconds(),
                        Instant.now()
                )
        );
    }

    @Override
    @Transactional
    public void logout(LogoutRequest request, Long currentUserId) {
        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new UserNotFoundException("Authenticated user not found"));

        User refreshOwner = refreshTokenService.validateAndGetUser(request.refreshToken());
        if (!refreshOwner.getUserId().equals(user.getUserId())) {
            throw new RefreshTokenException("Refresh token does not belong to the authenticated user");
        }

        refreshTokenService.revoke(request.refreshToken());
    }

    @Override
    @Transactional(readOnly = true)
    public UserAuthResponse validateToken(ValidateTokenRequest request) {
        JwtClaims claims = jwtService.parseAndValidate(request.token());
        User user = userRepository.findById(claims.userId())
                .orElseThrow(() -> new UserNotFoundException("User for token not found"));

        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new InvalidTokenException("User account is not active");
        }

        return userAuthMapper.toResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserAuthResponse currentUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Authenticated user not found"));

        return userAuthMapper.toResponse(user);
    }

    private void saveAudit(Long userId, String email, LoginStatus status, String ip, String agent) {
        loginAuditRepository.save(LoginAudit.builder()
                .userId(userId)
                .email(email)
                .status(status)
                .ipAddress(ip)
                .userAgent(agent)
                .build());
    }
}
