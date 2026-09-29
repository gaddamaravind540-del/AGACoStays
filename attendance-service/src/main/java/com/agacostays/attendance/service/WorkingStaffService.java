package com.agacostays.attendance.service;
import com.agacostays.attendance.dto.response.WorkingStaffResponse;
import java.util.List;
public interface WorkingStaffService {
    List<WorkingStaffResponse> working(Long branchId);
    List<com.agacostays.attendance.dto.response.AbsentStaffResponse> absent(Long branchId);
}
