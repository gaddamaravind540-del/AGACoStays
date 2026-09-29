package com.agacostays.support.dto.response;

import com.agacostays.support.enums.AssignmentStatus;
import lombok.*;
import java.time.OffsetDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class SupportAssignmentResponse {
    private Long assignmentId;
    private Long requestId;
    private Long assignedTo;
    private Long assignedBy;
    private AssignmentStatus assignmentStatus;
    private OffsetDateTime assignedAt;
    private OffsetDateTime completedAt;
}
