package com.agacostays.user.mapper;
import com.agacostays.user.dto.response.StaffResponse;
import com.agacostays.user.entity.Staff;
import com.agacostays.user.entity.StaffBranchMapping;
import org.springframework.stereotype.Component;
@Component
public class StaffMapper {
    public StaffResponse toResponse(Staff s, String roleName){
        return new StaffResponse(s.getStaffId(),s.getUser().getUserId(),s.getUser().getFullName(),s.getUser().getEmail(),
                s.getUser().getPhone(),s.getDepartment().name(),s.getShift().name(),s.getJoiningDate().toString(),roleName,s.getStatus().name());
    }
}
