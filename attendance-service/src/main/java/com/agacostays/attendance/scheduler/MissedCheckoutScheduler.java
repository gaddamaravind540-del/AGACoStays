package com.agacostays.attendance.scheduler;
import com.agacostays.attendance.repository.AttendanceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.*;
@Component @Slf4j
public class MissedCheckoutScheduler {
    private final AttendanceRepository repo;
    private final boolean enabled;
    public MissedCheckoutScheduler(AttendanceRepository repo,@Value("${attendance.missed-checkout.enabled}")boolean enabled){
        this.repo=repo;this.enabled=enabled;
    }
    @Scheduled(cron="0 0 * * * *")
    public void checkMissedCheckouts(){
        if(!enabled)return;
        LocalDate yesterday=LocalDate.now().minusDays(1);
        repo.findAll().stream().filter(a->yesterday.equals(a.getAttendanceDate())&&a.getCheckInTime()!=null&&a.getCheckOutTime()==null)
            .forEach(a->log.warn("Missed checkout detected attendanceId={} staffId={} date={}",a.getAttendanceId(),a.getStaffId(),a.getAttendanceDate()));
    }
}
