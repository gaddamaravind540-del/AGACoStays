package com.agacostays.notification.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final SecretKey key;
    public JwtAuthenticationFilter(@Value("${notification.jwt.secret}") String secret){
        this.key = Keys.hmacShaKeyFor(java.util.Base64.getDecoder().decode(secret));
    }
    @Override protected void doFilterInternal(HttpServletRequest req,HttpServletResponse res,FilterChain chain) throws ServletException,IOException {
        String h=req.getHeader("Authorization");
        if(h!=null && h.startsWith("Bearer ")){
            try{
                Claims c=Jwts.parser().verifyWith(key).build().parseSignedClaims(h.substring(7)).getPayload();
                String role=c.get("role",String.class);
                String authority=role==null?"ROLE_USER":(role.startsWith("ROLE_")?role:"ROLE_"+role);
                var principal=new JwtPrincipal(c.getSubject(),c);
                var auth=new UsernamePasswordAuthenticationToken(principal,null,List.of(new SimpleGrantedAuthority(authority)));
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch(Exception ignored) { }
        }
        chain.doFilter(req,res);
    }
    public record JwtPrincipal(String subject, Map<String,Object> claims) {
        public JwtPrincipal(String subject, Claims claims){this(subject, (Map<String,Object>)claims);}
    }
}
