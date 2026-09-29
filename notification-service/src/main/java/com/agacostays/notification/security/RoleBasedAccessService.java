package com.agacostays.notification.security;

import org.springframework.stereotype.Component;

@Component
public class RoleBasedAccessService {
    public boolean isRootAdmin(String role){return "ROLE_ROOT_ADMIN".equals(role) || "ROOT_ADMIN".equals(role);}
    public boolean isSystem(String role){return isRootAdmin(role) || "ROLE_SYSTEM".equals(role) || "SYSTEM".equals(role);}
}
