package com.agacostays.auth.security;

import com.agacostays.auth.constants.HeaderConstants;
import com.agacostays.auth.exception.TokenExpiredException;
import com.agacostays.auth.exception.InvalidTokenException;
import com.agacostays.auth.service.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7).trim();

        try {
            JwtClaims claims = jwtService.parseAndValidate(token);

            List<SimpleGrantedAuthority> authorities = claims.role() == null
                    ? List.of()
                    : List.of(new SimpleGrantedAuthority("ROLE_" + claims.role().toUpperCase()));

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            claims.email(),
                            null,
                            authorities
                    );

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            request.setAttribute(HeaderConstants.USER_ID_ATTRIBUTE, claims.userId());
            request.setAttribute(HeaderConstants.USER_EMAIL_ATTRIBUTE, claims.email());
            request.setAttribute(HeaderConstants.ROLE_ATTRIBUTE, claims.role());

            filterChain.doFilter(request, response);

        } catch (TokenExpiredException | InvalidTokenException | JwtException ex) {
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"success\":false,\"message\":\"Invalid or expired token\"}");
        }
    }
}
