package com.agacostays.attendance.controller;
import com.agacostays.attendance.dto.response.*;
import com.agacostays.attendance.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/hotel-branches")
public class AttendanceReportController {
    private final AttendanceReportService reports; private final WorkingStaffService workingService;
    public AttendanceReportController(AttendanceReportService reports,WorkingStaffService workingService){
        this.reports=reports;this.workingService=workingService;
    }
    @GetMapping("/{branchId}/attendance")
    public ResponseEntity<ApiResponse<PageResponse<AttendanceResponse>>> attendance(
        @PathVariable Long branchId,@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="20") int size){
        return ResponseEntity.ok(ApiResponse.ok("Branch attendance fetched",reports.branch(branchId,page,size)));
    }
    @GetMapping("/{branchId}/attendance/monthly")
    public ResponseEntity<ApiResponse<PageResponse<AttendanceResponse>>> monthly(
        @PathVariable Long branchId,@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="20") int size){
        return ResponseEntity.ok(ApiResponse.ok("Branch monthly attendance fetched",reports.branch(branchId,page,size)));
    }
    @GetMapping("/{branchId}/attendance/today/working")
    public ResponseEntity<ApiResponse<java.util.List<WorkingStaffResponse>>> working(@PathVariable Long branchId){
        return ResponseEntity.ok(ApiResponse.ok("Working staff fetched",workingService.working(branchId)));
    }
    @GetMapping("/{branchId}/attendance/today/absent")
    public ResponseEntity<ApiResponse<java.util.List<AbsentStaffResponse>>> absent(@PathVariable Long branchId){
        return ResponseEntity.ok(ApiResponse.ok("Absent staff fetched",workingService.absent(branchId)));
    }
}
