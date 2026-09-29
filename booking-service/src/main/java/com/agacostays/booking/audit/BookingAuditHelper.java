package com.agacostays.booking.audit;

import org.springframework.stereotype.Component;

@Component
public class BookingAuditHelper {
    public AuditLogRequest create(Long userId, String role, String action, String description) {
        return AuditLogRequest.builder()
                .userId(userId)
                .roleName(role)
                .moduleName("BOOKING")
                .action(action)
                .description(description)
                .build();
    }
}
