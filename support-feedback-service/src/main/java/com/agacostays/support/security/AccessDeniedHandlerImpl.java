package com.agacostays.support.security;

import jakarta.servlet.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override public void handle(HttpServletRequest req, HttpServletResponse res, AccessDeniedException e) throws IOException {
        res.setStatus(403); res.setContentType("application/json");
        res.getWriter().write("{\"status\":\"error\",\"message\":\"Access denied\"}");
    }
}
