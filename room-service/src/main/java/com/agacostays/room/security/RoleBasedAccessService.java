package com.agacostays.room.security;

import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class RoleBasedAccessService {

    public boolean hasAnyRole(String role, String... allowedRoles) {
        return role != null && Set.of(allowedRoles).contains(role);
    }
}
