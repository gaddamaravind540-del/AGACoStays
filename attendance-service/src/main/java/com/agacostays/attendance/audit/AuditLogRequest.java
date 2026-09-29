package com.agacostays.attendance.audit;
public record AuditLogRequest(String action,Long actorId,Long branchId,Long resourceId,String details){}
