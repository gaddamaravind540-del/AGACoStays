package com.agacostays.attendance.controller;
import com.agacostays.attendance.dto.request.*;
import com.agacostays.attendance.dto.response.*;
import com.agacostays.attendance.security.CurrentUserProvider;
import com.agacostays.attendance.service.AttendanceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/staff/my-branch/attendance")
public class StaffAttendanceController {
    private final AttendanceService service; private final CurrentUserProvider current;
    public StaffAttendanceController(AttendanceService service,CurrentUserProvider current){this.service=service;this.current=current;}
    @PostMapping("/check-in")
    public ResponseEntity<ApiResponse<CheckInResponse>> checkIn(@Valid @RequestBody CheckInRequest r){
        return ResponseEntity.ok(ApiResponse.ok("Check-in successful",service.checkIn(current.userId(),r)));
    }
    @PostMapping("/check-out")
    public ResponseEntity<ApiResponse<CheckOutResponse>> checkOut(@Valid @RequestBody CheckOutRequest r){
        return ResponseEntity.ok(ApiResponse.ok("Check-out successful",service.checkOut(current.userId(),r)));
    }
}
