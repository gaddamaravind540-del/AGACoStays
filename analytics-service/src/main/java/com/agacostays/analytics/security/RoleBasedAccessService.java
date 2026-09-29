package com.agacostays.analytics.security;
import org.springframework.stereotype.Component;
@Component
public class RoleBasedAccessService {
    public boolean managerOrRoot(String role){return "MANAGER".equals(role)||"ROOT_ADMIN".equals(role);}
    public boolean restaurantRole(String role){return "RESTAURANT_ADMIN".equals(role)||managerOrRoot(role);}
}
