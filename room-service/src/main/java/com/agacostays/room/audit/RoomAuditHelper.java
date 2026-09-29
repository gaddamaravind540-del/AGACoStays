package com.agacostays.room.audit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RoomAuditHelper {
    public void record(AuditLogRequest request) {
        log.info("AUDIT module={} action={} user={} role={}", request.moduleName(), request.action(), request.userId(), request.roleName());
    }
}
