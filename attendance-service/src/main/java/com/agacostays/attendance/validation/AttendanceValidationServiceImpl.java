package com.agacostays.attendance.validation;
import com.agacostays.attendance.entity.Attendance;
import com.agacostays.attendance.exception.BusinessRuleException;
import com.agacostays.attendance.repository.AttendanceRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
public class AttendanceValidationServiceImpl implements AttendanceValidationService {
    private final AttendanceRepository repo;
    public AttendanceValidationServiceImpl(AttendanceRepository repo){this.repo=repo;}
    public void validateNewCheckIn(Long staffId,Long branchId){
        repo.findByStaffIdAndAttendanceDate(staffId,LocalDate.now()).ifPresent(a->{
            if(a.getCheckInTime()!=null) throw new BusinessRuleException("Staff already checked in today");
        });
    }
    public void validateCheckOut(Attendance a){
        if(a.getCheckInTime()==null) throw new BusinessRuleException("Staff has not checked in");
        if(a.getCheckOutTime()!=null) throw new BusinessRuleException("Staff already checked out");
    }
}
