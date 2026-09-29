package com.agacostays.support.audit;
import org.springframework.stereotype.Component;
@Component
public class SupportFeedbackAuditHelper {
    public AuditLogRequest create(Long userId,String role,String action,String description){
        return AuditLogRequest.builder().userId(userId).roleName(role).moduleName("SUPPORT_FEEDBACK")
                .action(action).description(description).build();
    }
}
