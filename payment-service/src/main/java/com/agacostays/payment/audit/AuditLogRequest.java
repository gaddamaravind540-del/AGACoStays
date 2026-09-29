package com.agacostays.payment.audit; public record AuditLogRequest(String action,Long actorId,Long resourceId,String details) {}
