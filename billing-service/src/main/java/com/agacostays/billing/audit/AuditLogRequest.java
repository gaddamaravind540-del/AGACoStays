package com.agacostays.billing.audit;public record AuditLogRequest(String action,Long actorId,Long branchId,Long resourceId,String details){}
