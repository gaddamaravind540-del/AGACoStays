package com.agacostays.branch.security;
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
import java.io.IOException;
import java.util.*;
import javax.crypto.SecretKey;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
 private final SecretKey key;
 public JwtAuthenticationFilter(@Value("${app.jwt.secret}")String secret){key=Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));}
 protected void doFilterInternal(HttpServletRequest req,HttpServletResponse res,FilterChain chain)throws ServletException,IOException{
  String h=req.getHeader("Authorization");
  if(h==null||!h.startsWith("Bearer ")){chain.doFilter(req,res);return;}
  try{
   Claims c=Jwts.parser().verifyWith(key).build().parseSignedClaims(h.substring(7)).getPayload();
   Long uid=((Number)c.getOrDefault("userId",Long.parseLong(c.getSubject()))).longValue();
   String email=c.get("email",String.class),role=c.get("role",String.class);
   UsernamePasswordAuthenticationToken a=new UsernamePasswordAuthenticationToken(
     new CurrentUserProvider.Principal(uid,email,role),null,
     role==null?List.of():List.of(new SimpleGrantedAuthority("ROLE_"+role.toUpperCase()));
   SecurityContextHolder.getContext().setAuthentication(a);
   chain.doFilter(req,res);
  }catch(Exception e){
   SecurityContextHolder.clearContext();res.setStatus(401);res.setContentType("application/json");
   res.getWriter().write("{\"success\":false,\"message\":\"Invalid or expired JWT\"}");
  }
 }
}
