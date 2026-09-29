package com.agacostays.support.service;
import com.agacostays.support.dto.request.AssignSupportRequest;
import com.agacostays.support.dto.response.SupportAssignmentResponse;

public interface SupportAssignmentService {
    SupportAssignmentResponse assign(Long requestId, AssignSupportRequest request);
}
