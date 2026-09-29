package com.agacostays.attendance.controller;
import com.agacostays.attendance.dto.request.*;
import com.agacostays.attendance.dto.response.*;
import com.agacostays.attendance.security.CurrentUserProvider;
import com.agacostays.attendance.service.AttendanceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/manager")
public class ManagerAttendanceController {
    private final AttendanceService service; private final CurrentUserProvider current;
    public ManagerAttendanceController(AttendanceService service,CurrentUserProvider current){this.service=service;this.current=current;}
    @PostMapping("/staff/{staffId}/attendance")
    public ResponseEntity<ApiResponse<AttendanceResponse>> mark(
        @PathVariable Long staffId,@Valid @RequestBody MarkAttendanceRequest r){
        MarkAttendanceRequest normalized=new MarkAttendanceRequest(r.branchId(),staffId,r.attendanceDate(),r.status(),r.checkInTime(),
            r.checkOutTime(),r.leaveType(),r.remarks());
        return ResponseEntity.ok(ApiResponse.ok("Attendance marked",service.mark(normalized,current.userId())));
    }
    @PutMapping("/attendance/{attendanceId}")
    public ResponseEntity<ApiResponse<AttendanceResponse>> correct(
        @PathVariable Long attendanceId,@Valid @RequestBody AttendanceCorrectionRequest r){
        return ResponseEntity.ok(ApiResponse.ok("Attendance corrected",service.correct(attendanceId,r,current.userId())));
    }
}
