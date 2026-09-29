package com.agacostays.booking.security;

import org.springframework.stereotype.Component;

@Component
public class BranchAccessValidator {

    public void validate(Long requestedBranchId, Long assignedBranchId) {
        if (assignedBranchId == null || !requestedBranchId.equals(assignedBranchId)) {
            throw new com.agacostays.booking.exception.BranchAccessDeniedException();
        }
    }
}
