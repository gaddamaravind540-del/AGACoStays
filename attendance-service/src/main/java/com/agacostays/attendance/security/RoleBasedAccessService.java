package com.agacostays.attendance.security;
import org.springframework.stereotype.Component;
@Component
public class RoleBasedAccessService {
    public boolean isManager(String role){return "MANAGER".equals(role)||"ROOT_ADMIN".equals(role);}
    public boolean isReception(String role){return "RECEPTIONIST".equals(role)||isManager(role);}
    public boolean isStaff(String role){return role!=null && !isManager(role) && !"CUSTOMER".equals(role);}
}
