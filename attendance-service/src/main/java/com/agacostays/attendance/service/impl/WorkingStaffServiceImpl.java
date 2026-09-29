package com.agacostays.attendance.service.impl;
import com.agacostays.attendance.dto.response.*;
import com.agacostays.attendance.enums.AttendanceStatus;
import com.agacostays.attendance.repository.AttendanceRepository;
import com.agacostays.attendance.service.WorkingStaffService;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
@Service
public class WorkingStaffServiceImpl implements WorkingStaffService {
    private final AttendanceRepository repo;
    public WorkingStaffServiceImpl(AttendanceRepository repo){this.repo=repo;}
    public List<WorkingStaffResponse> working(Long branchId){
        return repo.findByBranchIdAndAttendanceDateOrderByStaffIdAsc(branchId,LocalDate.now()).stream()
            .filter(a->a.getCheckInTime()!=null&&a.getCheckOutTime()==null)
            .map(a->new WorkingStaffResponse(a.getAttendanceId(),a.getStaffId(),a.getBranchId(),a.getCheckInTime())).toList();
    }
    public List<AbsentStaffResponse> absent(Long branchId){
        return repo.findByBranchIdAndAttendanceDateAndStatus(branchId,LocalDate.now(),AttendanceStatus.ABSENT).stream()
            .map(a->new AbsentStaffResponse(a.getStaffId(),a.getBranchId(),a.getAttendanceDate(),a.getStatus().name())).toList();
    }
}
