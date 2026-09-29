package com.agacostays.user.security;
import org.springframework.stereotype.Component;
@Component
public class RoleBasedAccessService {
    public boolean isRootAdmin(String role){return "ROOT_ADMIN".equalsIgnoreCase(role);}
    public boolean isManager(String role){return "MANAGER".equalsIgnoreCase(role);}
    public boolean isStaff(String role){
        return switch(role == null ? "" : role.toUpperCase()){
            case "RECEPTIONIST","RESTAURANT_ADMIN","CHEF","SERVING_STAFF","HOUSEKEEPING_STAFF" -> true;
            default -> false;
        };
    }
}
