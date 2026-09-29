package com.agacostays.attendance.service.impl;

import com.agacostays.attendance.dto.response.*;
import com.agacostays.attendance.enums.AttendanceStatus;
import com.agacostays.attendance.entity.Attendance;
import com.agacostays.attendance.mapper.AttendanceMapper;
import com.agacostays.attendance.repository.AttendanceRepository;
import com.agacostays.attendance.service.AttendanceReportService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class AttendanceReportServiceImpl implements AttendanceReportService {
    private final AttendanceRepository repo; private final AttendanceMapper mapper;
    public AttendanceReportServiceImpl(AttendanceRepository repo,AttendanceMapper mapper){this.repo=repo;this.mapper=mapper;}
    public MonthlyAttendanceResponse monthly(Long staffId,int month,int year){
        LocalDate from=LocalDate.of(year,month,1), to=from.withDayOfMonth(from.lengthOfMonth());
        List<Attendance> list=repo.findByStaffIdAndAttendanceDateBetween(staffId,from,to);
        long present=list.stream().filter(a->a.getStatus()==AttendanceStatus.PRESENT).count();
        long absent=list.stream().filter(a->a.getStatus()==AttendanceStatus.ABSENT).count();
        long half=list.stream().filter(a->a.getStatus()==AttendanceStatus.HALF_DAY).count();
        long leave=list.stream().filter(a->a.getStatus()==AttendanceStatus.ON_LEAVE).count();
        BigDecimal hours=list.stream().map(Attendance::getWorkedHours).filter(java.util.Objects::nonNull)
            .reduce(BigDecimal.ZERO,BigDecimal::add);
        return new MonthlyAttendanceResponse(staffId,month,year,present,absent,half,leave,hours,
            list.stream().map(mapper::toResponse).toList());
    }
    public PageResponse<AttendanceResponse> branch(Long branchId,int page,int size){
        Pageable pageable=PageRequest.of(Math.max(page,0),Math.min(Math.max(size,1),100),Sort.by("attendanceDate").descending());
        List<Attendance> all=repo.findByBranchIdAndAttendanceDateOrderByStaffIdAsc(branchId,LocalDate.now());
        int from=Math.min(page*size,all.size()), to=Math.min(from+size,all.size());
        List<Attendance> sub=from<to?all.subList(from,to):List.of();
        int totalPages=(int)Math.ceil(all.size()/(double)size);
        return new PageResponse<>(sub.stream().map(mapper::toResponse).toList(),page,size,all.size(),totalPages);
    }
}
