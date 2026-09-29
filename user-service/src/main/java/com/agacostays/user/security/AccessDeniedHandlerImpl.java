package com.agacostays.user.security;
import jakarta.servlet.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
 public void handle(HttpServletRequest req,HttpServletResponse res,AccessDeniedException ex) throws java.io.IOException{
  res.setStatus(403);res.setContentType("application/json");res.getWriter().write("{\"success\":false,\"message\":\"Access denied\"}");
 }
}
