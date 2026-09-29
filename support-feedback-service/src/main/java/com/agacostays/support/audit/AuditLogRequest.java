package com.agacostays.support.audit;
import lombok.*;
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class AuditLogRequest {
    private Long userId;
    private String roleName;
    private String moduleName;
    private String action;
    private String description;
}
