package com.agacostays.booking.security;

import org.springframework.stereotype.Component;

@Component
public class RoleBasedAccessService {
    public boolean canManageBooking(String role) {
        return "ROOT_ADMIN".equals(role)
                || "MANAGER".equals(role)
                || "RECEPTIONIST".equals(role);
    }
}
