package com.agacostays.user.security;

import jakarta.servlet.http.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint {
	public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException ex)
			throws java.io.IOException {
		res.setStatus(401);
		res.setContentType("application/json");
		res.getWriter().write("{\"success\":false,\"message\":\"Authentication is required\"}");
	}
}
