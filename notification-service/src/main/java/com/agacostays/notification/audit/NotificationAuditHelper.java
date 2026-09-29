package com.agacostays.notification.audit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class NotificationAuditHelper {
    private static final Logger log=LoggerFactory.getLogger(NotificationAuditHelper.class);
    public void record(AuditLogRequest request){log.info("audit user={} role={} module={} action={} description={}", request.userId(), request.roleName(), request.moduleName(), request.action(), request.description());}
}
