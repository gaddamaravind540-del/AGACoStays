package com.agacostays.attendance.controller;
import com.agacostays.attendance.dto.response.*;
import com.agacostays.attendance.service.WorkingStaffService;
import com.agacostays.attendance.service.AttendanceReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/manager/hotel-branches")
public class BranchAttendanceController {
    private final WorkingStaffService working; private final AttendanceReportService reports;
    public BranchAttendanceController(WorkingStaffService working,AttendanceReportService reports){this.working=working;this.reports=reports;}
    @GetMapping("/{branchId}/working-staff")
    public ResponseEntity<ApiResponse<java.util.List<WorkingStaffResponse>>> working(@PathVariable Long branchId){
        return ResponseEntity.ok(ApiResponse.ok("Working staff fetched",working.working(branchId)));
    }
    @GetMapping("/{branchId}/absent-staff")
    public ResponseEntity<ApiResponse<java.util.List<AbsentStaffResponse>>> absent(@PathVariable Long branchId){
        return ResponseEntity.ok(ApiResponse.ok("Absent staff fetched",working.absent(branchId)));
    }
    @GetMapping("/{branchId}/attendance")
    public ResponseEntity<ApiResponse<PageResponse<AttendanceResponse>>> attendance(
        @PathVariable Long branchId,@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="20") int size){
        return ResponseEntity.ok(ApiResponse.ok("Branch attendance fetched",reports.branch(branchId,page,size)));
    }
}
