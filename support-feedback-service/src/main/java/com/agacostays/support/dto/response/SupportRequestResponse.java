package com.agacostays.support.dto.response;

import com.agacostays.support.enums.*;
import lombok.*;

import java.time.OffsetDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class SupportRequestResponse {
    private Long requestId;
    private Long branchId;
    private Long customerId;
    private Long bookingId;
    private SupportRequestType requestType;
    private String subject;
    private String description;
    private SupportPriority priority;
    private SupportStatus status;
    private Long assignedTo;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
