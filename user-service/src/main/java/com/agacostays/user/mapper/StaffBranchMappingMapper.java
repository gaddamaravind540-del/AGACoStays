package com.agacostays.user.mapper;
import com.agacostays.user.dto.response.StaffBranchMappingResponse;
import com.agacostays.user.entity.StaffBranchMapping;
import org.springframework.stereotype.Component;
@Component
public class StaffBranchMappingMapper {
    public StaffBranchMappingResponse toResponse(StaffBranchMapping m){
        return new StaffBranchMappingResponse(
            m.getMappingId(),m.getStaff().getStaffId(),m.getBranchId(),m.getRole().getRoleName(),
            m.getDepartment().name(),m.getShift().name(),m.getAssignedFrom().toString(),
            m.getAssignedTo()==null?null:m.getAssignedTo().toString(),m.getStatus().name());
    }
}
