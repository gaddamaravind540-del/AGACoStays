package com.agacostays.room.security;

import com.agacostays.room.exception.BranchAccessDeniedException;
import org.springframework.stereotype.Component;

@Component
public class BranchAccessValidator {

    private final CurrentUserProvider currentUserProvider;

    public BranchAccessValidator(CurrentUserProvider currentUserProvider) {
        this.currentUserProvider = currentUserProvider;
    }

    public void validate(Long requestedBranchId) {
        CurrentUser current = currentUserProvider.getCurrentUser();
        if (current.role() == null) {
            throw new BranchAccessDeniedException("Authentication is required");
        }
        if ("ROOT_ADMIN".equals(current.role()) || "MANAGER".equals(current.role())) {
            return;
        }
        if (current.branchId() == null || !current.branchId().equals(requestedBranchId)) {
            throw new BranchAccessDeniedException("You do not have access to branch " + requestedBranchId);
        }
    }
}
