package com.agacostays.attendance.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final String secret;
    private final String issuer;
    public JwtAuthenticationFilter(@Value("${security.jwt.secret}") String secret,
                                   @Value("${security.jwt.issuer}") String issuer){
        this.secret=secret;this.issuer=issuer;
    }
    @Override protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain chain)
        throws ServletException,IOException {
        String header=request.getHeader("Authorization");
        if(StringUtils.hasText(header)&&header.startsWith("Bearer ")){
            try{
                String token=header.substring(7);
                var key=new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),"HmacSHA256");
                Claims claims=Jwts.parser().verifyWith(key).requireIssuer(issuer).build().parseSignedClaims(token).getPayload();
                Long userId=claims.get("userId",Long.class);
                String role=claims.get("role",String.class);
                String branchId=claims.get("branchId",String.class);
                if(role==null) role=claims.get("roles",String.class);
                if(role!=null){
                    var auth=new UsernamePasswordAuthenticationToken(
                        userId==null?claims.getSubject():userId.toString(),null,
                        List.of(new SimpleGrantedAuthority("ROLE_"+role.replace("ROLE_",""))));
                    request.setAttribute("attendance.userId",userId);
                    request.setAttribute("attendance.branchId",branchId==null?null:Long.valueOf(branchId));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }catch(Exception ignored){}
        }
        chain.doFilter(request,response);
    }
}
