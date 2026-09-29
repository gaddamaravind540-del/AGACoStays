package com.agacostays.payment.security;
import jakarta.servlet.http.*; import org.springframework.security.access.AccessDeniedException; import org.springframework.security.web.access.AccessDeniedHandler; import org.springframework.stereotype.Component; import java.io.IOException;
@Component public class AccessDeniedHandlerImpl implements AccessDeniedHandler { public void handle(HttpServletRequest r,HttpServletResponse s,AccessDeniedException e)throws IOException{s.setStatus(403);s.setContentType("application/json");s.getWriter().write("{\"success\":false,\"message\":\"Access denied\"}");} }
