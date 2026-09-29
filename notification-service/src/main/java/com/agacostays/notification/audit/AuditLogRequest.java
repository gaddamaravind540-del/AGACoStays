package com.agacostays.notification.audit;

public record AuditLogRequest(Long userId, String roleName, String moduleName, String action, String description, String oldValue, String newValue) {}
