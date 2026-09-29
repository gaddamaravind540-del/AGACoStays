package com.agacostays.analytics.security;
import org.springframework.stereotype.Component;
@Component
public class BranchAccessValidator {
    public boolean canAccess(Long branchId){return branchId!=null;}
}
