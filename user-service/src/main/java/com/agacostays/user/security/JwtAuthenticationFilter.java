package com.agacostays.user.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;
import java.util.Base64;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final SecretKey key;
    public JwtAuthenticationFilter(@Value("${app.jwt.secret}") String secret){
        this.key=Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
    }
    @Override
    protected void doFilterInternal(HttpServletRequest req,HttpServletResponse res,FilterChain chain) throws ServletException,IOException{
        String h=req.getHeader("Authorization");
        if(h==null || !h.startsWith("Bearer ")){chain.doFilter(req,res);return;}
        try{
            Claims claims=Jwts.parser().verifyWith(key).build().parseSignedClaims(h.substring(7)).getPayload();
            Long userId = parseUserId(claims.get("userId"));
            String email = claims.get("email",String.class);
            String role = claims.get("role",String.class);
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(
                            new CurrentUserProvider.UserPrincipal(userId,email,role),
                            null,
                            role==null?List.of():List.of(new SimpleGrantedAuthority("ROLE_"+role.toUpperCase())));
            SecurityContextHolder.getContext().setAuthentication(auth);
            chain.doFilter(req,res);
        } catch (JwtException | IllegalArgumentException ex){
            SecurityContextHolder.clearContext();
            res.setStatus(401);
            res.setContentType("application/json");
            res.getWriter().write("{\"success\":false,\"message\":\"Invalid or expired JWT\"}");
        }
    }
    private Long parseUserId(Object v){
        if(v instanceof Number n) return n.longValue();
        return Long.valueOf(String.valueOf(v));
    }
}
