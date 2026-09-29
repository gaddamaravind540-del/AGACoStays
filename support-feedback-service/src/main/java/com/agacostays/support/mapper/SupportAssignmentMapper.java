package com.agacostays.support.mapper;
import com.agacostays.support.dto.response.SupportAssignmentResponse;
import com.agacostays.support.entity.SupportAssignment;
import org.springframework.stereotype.Component;

@Component
public class SupportAssignmentMapper {
    public SupportAssignmentResponse toResponse(SupportAssignment x) {
        return SupportAssignmentResponse.builder().assignmentId(x.getAssignmentId()).requestId(x.getRequestId())
                .assignedTo(x.getAssignedTo()).assignedBy(x.getAssignedBy()).assignmentStatus(x.getAssignmentStatus())
                .assignedAt(x.getAssignedAt()).completedAt(x.getCompletedAt()).build();
    }
}
