package com.agacostays.branch.audit;
public record AuditLogRequest(Long actorUserId,Long branchId,String action,String description) {}
