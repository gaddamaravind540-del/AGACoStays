package com.agacostays.attendance.audit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
@Component @Slf4j
public class AttendanceAuditHelper {
    public void record(AuditLogRequest r){
        log.info("AUDIT action={} actorId={} branchId={} resourceId={} details={}",
            r.action(),r.actorId(),r.branchId(),r.resourceId(),r.details());
    }
}
