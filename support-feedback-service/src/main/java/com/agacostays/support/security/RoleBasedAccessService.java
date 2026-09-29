package com.agacostays.support.security;

import org.springframework.stereotype.Component;

@Component
public class RoleBasedAccessService {
    public boolean isStaff(String role) {
        return switch (role) {
            case "MANAGER","RECEPTIONIST","RESTAURANT_ADMIN","CHEF","SERVING_STAFF","HOUSEKEEPING_STAFF","ROOT_ADMIN" -> true;
            default -> false;
        };
    }
}
