package com.agacostays.user.security;

import com.agacostays.user.entity.StaffBranchMapping;
import com.agacostays.user.enums.BranchAssignmentStatus;
import com.agacostays.user.exception.BranchMappingException;
import com.agacostays.user.repository.StaffBranchMappingRepository;
import org.springframework.stereotype.Component;

@Component
public class BranchAccessValidator {
    private final StaffBranchMappingRepository repository;
    public BranchAccessValidator(StaffBranchMappingRepository repository){this.repository=repository;}

    public void requireAccess(Long userId,Long branchId,String role){
        if(role==null) throw new BranchMappingException("Role is missing");
        if("ROOT_ADMIN".equalsIgnoreCase(role) || "MANAGER".equalsIgnoreCase(role)) return;
        boolean allowed=repository.findByStaff_User_UserIdAndBranchIdAndStatus(userId,branchId,BranchAssignmentStatus.ACTIVE).isPresent();
        if(!allowed) throw new BranchMappingException("User is not assigned to branch "+branchId);
    }
}
