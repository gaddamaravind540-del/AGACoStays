package com.agacostays.support.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserProvider {
    public Long userId() {
        return Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
    }
    public String role() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .findFirst().map(a -> a.getAuthority().replace("ROLE_", "")).orElse("UNKNOWN");
    }
}
