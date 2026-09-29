package com.agacostays.support.service.impl;

import com.agacostays.support.dto.request.AssignSupportRequest;
import com.agacostays.support.dto.response.SupportAssignmentResponse;
import com.agacostays.support.entity.SupportAssignment;
import com.agacostays.support.enums.*;
import com.agacostays.support.exception.ResourceNotFoundException;
import com.agacostays.support.mapper.SupportAssignmentMapper;
import com.agacostays.support.repository.*;
import com.agacostays.support.security.CurrentUserProvider;
import com.agacostays.support.service.SupportAssignmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SupportAssignmentServiceImpl implements SupportAssignmentService {

    private final SupportAssignmentRepository assignmentRepo;
    private final CustomerSupportRequestRepository requestRepo;
    private final CurrentUserProvider currentUser;
    private final SupportAssignmentMapper mapper;

    public SupportAssignmentServiceImpl(SupportAssignmentRepository assignmentRepo,
                                        CustomerSupportRequestRepository requestRepo,
                                        CurrentUserProvider currentUser,
                                        SupportAssignmentMapper mapper) {
        this.assignmentRepo=assignmentRepo;this.requestRepo=requestRepo;this.currentUser=currentUser;this.mapper=mapper;
    }

    @Override @Transactional
    public SupportAssignmentResponse assign(Long requestId, AssignSupportRequest request) {
        requestRepo.findById(requestId)
                .orElseThrow(()->new ResourceNotFoundException("Support request not found"));

        SupportAssignment a=assignmentRepo.findByRequestId(requestId)
                .orElseGet(()->SupportAssignment.builder().requestId(requestId).build());

        a.setAssignedTo(request.getAssignedTo());
        a.setAssignedBy(currentUser.userId());
        a.setAssignmentStatus(AssignmentStatus.ASSIGNED);

        return mapper.toResponse(assignmentRepo.save(a));
    }
}
