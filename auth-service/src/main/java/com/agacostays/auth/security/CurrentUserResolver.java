package com.agacostays.auth.security;

import com.agacostays.auth.constants.HeaderConstants;
import com.agacostays.auth.exception.InvalidTokenException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserResolver {

    public Long userId(HttpServletRequest request) {
        Object value = request.getAttribute(HeaderConstants.USER_ID_ATTRIBUTE);
        if (value instanceof Number number) {
            return number.longValue();
        }
        if (value != null) {
            try {
                return Long.parseLong(String.valueOf(value));
            } catch (NumberFormatException ignored) {}
        }
        throw new InvalidTokenException("Authenticated user id is missing");
    }

    public String email(HttpServletRequest request) {
        Object value = request.getAttribute(HeaderConstants.USER_EMAIL_ATTRIBUTE);
        return value == null ? null : String.valueOf(value);
    }

    public String role(HttpServletRequest request) {
        Object value = request.getAttribute(HeaderConstants.ROLE_ATTRIBUTE);
        return value == null ? null : String.valueOf(value);
    }
}
