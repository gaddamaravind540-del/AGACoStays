package com.agacostays.room.audit;

public record AuditLogRequest(Long userId, String roleName, String moduleName, String action, String description,
                              String oldValue, String newValue, String ipAddress, String userAgent) {}
