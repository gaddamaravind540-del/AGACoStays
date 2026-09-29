package com.agacostays.attendance.controller;

import com.agacostays.attendance.dto.request.*;
import com.agacostays.attendance.dto.response.*;
import com.agacostays.attendance.security.CurrentUserProvider;
import com.agacostays.attendance.service.AttendanceService;
import com.agacostays.attendance.service.AttendanceReportService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {
    private final AttendanceService service; private final AttendanceReportService reports; private final CurrentUserProvider current;
    public AttendanceController(AttendanceService service,AttendanceReportService reports,CurrentUserProvider current){
        this.service=service;this.reports=reports;this.current=current;
    }

    @PostMapping("/check-in")
    public ResponseEntity<ApiResponse<CheckInResponse>> checkIn(@Valid @RequestBody CheckInRequest r){
        return ResponseEntity.ok(ApiResponse.ok("Check-in successful",service.checkIn(current.userId(),r)));
    }
    @PostMapping("/check-out")
    public ResponseEntity<ApiResponse<CheckOutResponse>> checkOut(@Valid @RequestBody CheckOutRequest r){
        return ResponseEntity.ok(ApiResponse.ok("Check-out successful",service.checkOut(current.userId(),r)));
    }
    @GetMapping("/my-attendance")
    public ResponseEntity<ApiResponse<java.util.List<AttendanceResponse>>> my(){
        return ResponseEntity.ok(ApiResponse.ok("Attendance fetched",service.myAttendance(current.userId())));
    }
    @PostMapping("/mark")
    public ResponseEntity<ApiResponse<AttendanceResponse>> mark(@Valid @RequestBody MarkAttendanceRequest r){
        return ResponseEntity.ok(ApiResponse.ok("Attendance marked",service.mark(r,current.userId())));
    }
    @PostMapping("/mark-absent")
    public ResponseEntity<ApiResponse<AttendanceResponse>> markAbsent(@RequestBody MarkAttendanceRequest r){
        MarkAttendanceRequest fixed=new MarkAttendanceRequest(r.branchId(),r.staffId(),r.attendanceDate(),
            com.agacostays.attendance.enums.AttendanceStatus.ABSENT,null,null,
            com.agacostays.attendance.enums.LeaveType.NONE,r.remarks());
        return ResponseEntity.ok(ApiResponse.ok("Staff marked absent",service.mark(fixed,current.userId())));
    }
    @GetMapping("/staff/{staffId}")
    public ResponseEntity<ApiResponse<java.util.List<AttendanceResponse>>> staff(@PathVariable Long staffId){
        return ResponseEntity.ok(ApiResponse.ok("Staff attendance fetched",service.staffAttendance(staffId)));
    }
    @GetMapping("/staff/{staffId}/monthly")
    public ResponseEntity<ApiResponse<MonthlyAttendanceResponse>> monthly(
        @PathVariable Long staffId,@RequestParam int month,@RequestParam int year){
        return ResponseEntity.ok(ApiResponse.ok("Monthly attendance fetched",reports.monthly(staffId,month,year)));
    }
    @PutMapping("/{attendanceId}")
    public ResponseEntity<ApiResponse<AttendanceResponse>> correct(
        @PathVariable Long attendanceId,@Valid @RequestBody AttendanceCorrectionRequest r){
        return ResponseEntity.ok(ApiResponse.ok("Attendance corrected",service.correct(attendanceId,r,current.userId())));
    }
    @DeleteMapping("/{attendanceId}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long attendanceId){
        service.delete(attendanceId); return ResponseEntity.ok(ApiResponse.ok("Attendance deleted",null));
    }
    @GetMapping("/{attendanceId}")
    public ResponseEntity<ApiResponse<AttendanceResponse>> get(@PathVariable Long attendanceId){
        return ResponseEntity.ok(ApiResponse.ok("Attendance fetched",service.get(attendanceId)));
    }
}
