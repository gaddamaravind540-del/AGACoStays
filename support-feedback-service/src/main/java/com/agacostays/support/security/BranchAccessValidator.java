package com.agacostays.support.security;

import org.springframework.stereotype.Component;

@Component
public class BranchAccessValidator {
    public void validate(Long requestedBranchId, Long assignedBranchId) {
        if (assignedBranchId == null || !requestedBranchId.equals(assignedBranchId)) {
            throw new com.agacostays.support.exception.AccessDeniedException("Branch access denied");
        }
    }
}
