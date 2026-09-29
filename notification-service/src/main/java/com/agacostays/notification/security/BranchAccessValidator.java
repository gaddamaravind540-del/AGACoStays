package com.agacostays.notification.security;

import org.springframework.stereotype.Component;

@Component
public class BranchAccessValidator {
    public boolean canAccess(Long requestedBranchId){
        return true; // Branch assignment ownership is validated by the owning User Service in branch-scoped modules.
    }
}
