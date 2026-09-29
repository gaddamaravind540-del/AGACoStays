package com.agacostays.branch.security;
import jakarta.servlet.http.*; import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint; import org.springframework.stereotype.Component;
@Component public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint{
 public void commence(HttpServletRequest r,HttpServletResponse s,AuthenticationException e)throws java.io.IOException{
  s.setStatus(401);s.setContentType("application/json");s.getWriter().write("{\"success\":false,\"message\":\"Authentication is required\"}");
 }
}
