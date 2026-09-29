package com.agacostays.attendance.security;
import jakarta.servlet.http.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import java.io.IOException;
@Component
public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint {
    public void commence(HttpServletRequest r,HttpServletResponse s,AuthenticationException e)throws IOException{
        s.sendError(401,"Authentication required");
    }
}
