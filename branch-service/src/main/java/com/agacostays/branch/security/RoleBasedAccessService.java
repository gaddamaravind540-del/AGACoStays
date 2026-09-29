package com.agacostays.branch.security;
import org.springframework.stereotype.Component;
@Component public class RoleBasedAccessService {
 public boolean isRootAdmin(String r){return "ROOT_ADMIN".equalsIgnoreCase(r);}
 public boolean isManager(String r){return "MANAGER".equalsIgnoreCase(r);}
}
