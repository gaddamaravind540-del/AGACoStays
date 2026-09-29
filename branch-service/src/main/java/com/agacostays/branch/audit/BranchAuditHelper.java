package com.agacostays.branch.audit;
import org.slf4j.Logger; import org.slf4j.LoggerFactory; import org.springframework.stereotype.Component;
@Component public class BranchAuditHelper{
 private static final Logger log=LoggerFactory.getLogger(BranchAuditHelper.class);
 public void log(AuditLogRequest r){log.info("branch_audit actor={} branch={} action={} description={}",r.actorUserId(),r.branchId(),r.action(),r.description());}
}
