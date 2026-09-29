package com.agacostays.payment.security;

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
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
 private final String secret;
 public JwtAuthenticationFilter(@Value("${app.jwt.secret}") String secret){this.secret=secret;}
 @Override protected void doFilterInternal(HttpServletRequest req,HttpServletResponse res,FilterChain chain) throws ServletException, java.io.IOException {
  String h=req.getHeader("Authorization");
  if(h!=null && h.startsWith("Bearer ")){ try {
    Claims c=Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8))).build().parseSignedClaims(h.substring(7)).getPayload();
    String role=c.get("role",String.class); if(role==null)role="CUSTOMER";
    Map<String,Object> details=new HashMap<>();
    details.put("userId", value(c,"userId")); details.put("customerId", value(c,"customerId")); details.put("branchId", value(c,"branchId")); details.put("role", role);
    var auth=new UsernamePasswordAuthenticationToken(c.getSubject(),null,List.of(new SimpleGrantedAuthority("ROLE_"+role))); auth.setDetails(details); SecurityContextHolder.getContext().setAuthentication(auth);
  } catch(JwtException|IllegalArgumentException ignored){} }
  chain.doFilter(req,res);
 }
 private Object value(Claims c,String k){Object v=c.get(k); return v!=null?v:c.getSubject();}
}
