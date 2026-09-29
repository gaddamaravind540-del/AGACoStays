package com.agacostays.attendance.security;
import org.springframework.stereotype.Component;
@Component
public class BranchAccessValidator {
    public boolean canAccess(Long requestedBranchId, Long claimBranchId, String role) {
        return requestedBranchId != null && ("MANAGER".equals(role) || "RECEPTIONIST".equals(role)
            || "ROOT_ADMIN".equals(role) || claimBranchId == null || requestedBranchId.equals(claimBranchId));
    }
}
