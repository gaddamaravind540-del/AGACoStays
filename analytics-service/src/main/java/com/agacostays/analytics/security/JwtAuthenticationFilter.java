package com.agacostays.analytics.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.*;
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
    private final String secret,issuer;
    public JwtAuthenticationFilter(@Value("${security.jwt.secret}")String secret,@Value("${security.jwt.issuer}")String issuer){
        this.secret=secret;this.issuer=issuer;
    }
    @Override protected void doFilterInternal(HttpServletRequest req,HttpServletResponse res,FilterChain chain)
        throws ServletException,IOException{
        String h=req.getHeader("Authorization");
        if(StringUtils.hasText(h)&&h.startsWith("Bearer ")){
            try{
                var key=new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),"HmacSHA256");
                Claims c=Jwts.parser().verifyWith(key).requireIssuer(issuer).build()
                    .parseSignedClaims(h.substring(7)).getPayload();
                Long id=c.get("userId",Long.class);
                String role=c.get("role",String.class);
                if(role==null)role=c.get("roles",String.class);
                if(role!=null){
                    var auth=new UsernamePasswordAuthenticationToken(id==null?c.getSubject():id.toString(),null,
                        List.of(new SimpleGrantedAuthority("ROLE_"+role.replace("ROLE_",""))));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }catch(Exception ignored){}
        }
        chain.doFilter(req,res);
    }
}
