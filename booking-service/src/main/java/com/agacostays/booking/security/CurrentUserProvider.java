package com.agacostays.booking.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserProvider {

    public Long getCurrentUserId() {
        String subject = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            return Long.valueOf(subject);
        } catch (NumberFormatException ex) {
            throw new IllegalStateException("JWT subject must contain numeric user id");
        }
    }

    public String getCurrentRole() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream()
                .findFirst()
                .map(a -> a.getAuthority().replace("ROLE_", ""))
                .orElse("UNKNOWN");
    }

    public boolean isCustomer() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream().anyMatch(a -> a.getAuthority().equals("ROLE_CUSTOMER"));
    }

    public boolean isAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    }
}
