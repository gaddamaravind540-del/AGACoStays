package com.agacostays.restaurant.audit;
import org.springframework.stereotype.Component;
@Component
public class RestaurantAuditHelper {
    public AuditLogRequest request(Long userId,String role,String action,String description){
        return AuditLogRequest.builder().userId(userId).roleName(role).moduleName("RESTAURANT").action(action).description(description).build();
    }
}
