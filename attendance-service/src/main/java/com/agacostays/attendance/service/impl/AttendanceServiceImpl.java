package com.agacostays.attendance.service.impl;

import com.agacostays.attendance.audit.*;
import com.agacostays.attendance.client.UserServiceClient;
import com.agacostays.attendance.dto.request.*;
import com.agacostays.attendance.dto.response.*;
import com.agacostays.attendance.entity.*;
import com.agacostays.attendance.enums.*;
import com.agacostays.attendance.event.*;
import com.agacostays.attendance.event.producer.AttendanceEventProducer;
import com.agacostays.attendance.exception.*;
import com.agacostays.attendance.mapper.AttendanceMapper;
import com.agacostays.attendance.repository.*;
import com.agacostays.attendance.service.AttendanceService;
import com.agacostays.attendance.validation.AttendanceValidationService;
import com.agacostays.attendance.util.AttendanceUtil;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class AttendanceServiceImpl implements AttendanceService {
    private final AttendanceRepository repo;
    private final AttendanceCorrectionRepository corrections;
    private final AttendanceMapper mapper;
    private final AttendanceValidationService validation;
    private final UserServiceClient userClient;
    private final AttendanceEventProducer producer;
    private final AttendanceAuditHelper audit;

    public AttendanceServiceImpl(AttendanceRepository repo,AttendanceCorrectionRepository corrections,
        AttendanceMapper mapper,AttendanceValidationService validation,UserServiceClient userClient,
        AttendanceEventProducer producer,AttendanceAuditHelper audit){
        this.repo=repo;this.corrections=corrections;this.mapper=mapper;this.validation=validation;
        this.userClient=userClient;this.producer=producer;this.audit=audit;
    }

    @Override @Transactional
    public CheckInResponse checkIn(Long staffId,CheckInRequest request){
        if(!userClient.validateUser(staffId)) throw new ResourceNotFoundException("Staff/user not found: "+staffId);
        validation.validateNewCheckIn(staffId,request.branchId());
        LocalDate date=LocalDate.now();
        Attendance a=repo.findByStaffIdAndAttendanceDate(staffId,date).orElseGet(()->Attendance.builder()
            .staffId(staffId).branchId(request.branchId()).attendanceDate(date)
            .status(AttendanceStatus.PRESENT).source(AttendanceSource.SELF_CHECK_IN)
            .leaveType(LeaveType.NONE).createdBy(staffId).build());
        if(a.getCheckInTime()!=null) throw new DuplicateResourceException("Already checked in");
        a.setBranchId(request.branchId()); a.setCheckInTime(LocalTime.now());
        a.setStatus(AttendanceStatus.PRESENT); a.setSource(AttendanceSource.SELF_CHECK_IN);
        a=repo.save(a);
        producer.checkedIn(new StaffCheckedInEvent(a.getAttendanceId(),a.getStaffId(),a.getBranchId(),a.getAttendanceDate(),a.getCheckInTime()));
        producer.attendanceMarked(new AttendanceMarkedEvent(a.getAttendanceId(),a.getStaffId(),a.getBranchId(),a.getStatus().name(),a.getAttendanceDate()));
        audit.record(new AuditLogRequest("STAFF_CHECK_IN",staffId,a.getBranchId(),a.getAttendanceId(),"self check-in"));
        return new CheckInResponse(a.getAttendanceId(),a.getStaffId(),a.getBranchId(),a.getCheckInTime(),CheckInOutStatus.SUCCESS.name());
    }

    @Override @Transactional
    public CheckOutResponse checkOut(Long staffId,CheckOutRequest request){
        Attendance a=repo.findByStaffIdAndAttendanceDate(staffId,LocalDate.now())
            .orElseThrow(()->new ResourceNotFoundException("Today's attendance not found"));
        validation.validateCheckOut(a);
        a.setCheckOutTime(LocalTime.now());
        a.setWorkedHours(AttendanceUtil.workedHours(a.getCheckInTime(),a.getCheckOutTime()));
        repo.save(a);
        producer.checkedOut(new StaffCheckedOutEvent(a.getAttendanceId(),a.getStaffId(),a.getBranchId(),a.getAttendanceDate(),a.getCheckOutTime(),a.getWorkedHours()));
        audit.record(new AuditLogRequest("STAFF_CHECK_OUT",staffId,a.getBranchId(),a.getAttendanceId(),"self check-out"));
        return new CheckOutResponse(a.getAttendanceId(),a.getStaffId(),a.getCheckOutTime(),a.getWorkedHours(),CheckInOutStatus.SUCCESS.name());
    }

    @Override @Transactional
    public AttendanceResponse mark(MarkAttendanceRequest r,Long actorId){
        if(!userClient.validateUser(r.staffId())) throw new ResourceNotFoundException("Staff/user not found: "+r.staffId());
        if(repo.findByStaffIdAndAttendanceDate(r.staffId(),r.attendanceDate()).isPresent())
            throw new DuplicateResourceException("Attendance already exists for staff on "+r.attendanceDate());
        LocalTime in=r.checkInTime(), out=r.checkOutTime();
        Attendance a=Attendance.builder().branchId(r.branchId()).staffId(r.staffId()).attendanceDate(r.attendanceDate())
            .status(r.status()).source(AttendanceSource.MANUAL)
            .leaveType(r.leaveType()==null?LeaveType.NONE:r.leaveType())
            .checkInTime(in).checkOutTime(out)
            .workedHours(AttendanceUtil.workedHours(in,out))
            .remarks(r.remarks()).createdBy(actorId).updatedBy(actorId).build();
        a=repo.save(a);
        producer.attendanceMarked(new AttendanceMarkedEvent(a.getAttendanceId(),a.getStaffId(),a.getBranchId(),a.getStatus().name(),a.getAttendanceDate()));
        audit.record(new AuditLogRequest("MANUAL_ATTENDANCE_MARK",actorId,a.getBranchId(),a.getAttendanceId(),"status="+a.getStatus()));
        return mapper.toResponse(a);
    }

    @Override @Transactional
    public AttendanceResponse correct(Long attendanceId,AttendanceCorrectionRequest r,Long actorId){
        Attendance a=repo.findById(attendanceId).orElseThrow(()->new ResourceNotFoundException("Attendance not found: "+attendanceId));
        if(r.requestedStatus()!=null)a.setStatus(r.requestedStatus());
        if(r.requestedCheckIn()!=null)a.setCheckInTime(r.requestedCheckIn());
        if(r.requestedCheckOut()!=null)a.setCheckOutTime(r.requestedCheckOut());
        a.setWorkedHours(AttendanceUtil.workedHours(a.getCheckInTime(),a.getCheckOutTime()));
        a.setUpdatedBy(actorId);
        repo.save(a);
        corrections.save(AttendanceCorrection.builder().attendanceId(a.getAttendanceId()).staffId(a.getStaffId())
            .attendanceDate(a.getAttendanceDate()).requestedStatus(r.requestedStatus())
            .requestedCheckIn(r.requestedCheckIn()).requestedCheckOut(r.requestedCheckOut())
            .reason(r.reason()).status(AttendanceCorrectionStatus.APPROVED)
            .requestedBy(actorId).approvedBy(actorId).build());
        producer.corrected(new AttendanceCorrectedEvent(a.getAttendanceId(),a.getStaffId(),a.getBranchId(),a.getAttendanceDate()));
        audit.record(new AuditLogRequest("ATTENDANCE_CORRECTED",actorId,a.getBranchId(),a.getAttendanceId(),r.reason()));
        return mapper.toResponse(a);
    }

    @Override public List<AttendanceResponse> myAttendance(Long staffId){
        LocalDate to=LocalDate.now(), from=to.minusDays(30);
        return repo.findByStaffIdAndAttendanceDateBetweenOrderByAttendanceDateDesc(staffId,from,to).stream().map(mapper::toResponse).toList();
    }
    @Override public AttendanceResponse get(Long attendanceId){
        return repo.findById(attendanceId).map(mapper::toResponse)
            .orElseThrow(()->new ResourceNotFoundException("Attendance not found: "+attendanceId));
    }
    @Override public List<AttendanceResponse> staffAttendance(Long staffId){
        return repo.findByStaffIdAndAttendanceDateBetweenOrderByAttendanceDateDesc(staffId,LocalDate.now().minusDays(31),LocalDate.now())
            .stream().map(mapper::toResponse).toList();
    }
    @Override @Transactional public void delete(Long attendanceId){
        if(!repo.existsById(attendanceId)) throw new ResourceNotFoundException("Attendance not found: "+attendanceId);
        repo.deleteById(attendanceId);
    }
}
