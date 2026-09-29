package com.agacostays.attendance.event.producer;
import com.agacostays.attendance.constants.KafkaTopicConstants;
import com.agacostays.attendance.event.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
@Component
public class AttendanceEventProducer {
    private final KafkaTemplate<String,Object> kafka;
    public AttendanceEventProducer(KafkaTemplate<String,Object> kafka){this.kafka=kafka;}
    public void attendanceMarked(AttendanceMarkedEvent e){kafka.send(KafkaTopicConstants.ATTENDANCE_EVENTS,String.valueOf(e.attendanceId()),e);}
    public void checkedIn(StaffCheckedInEvent e){kafka.send(KafkaTopicConstants.ATTENDANCE_EVENTS,String.valueOf(e.attendanceId()),e);}
    public void checkedOut(StaffCheckedOutEvent e){kafka.send(KafkaTopicConstants.ATTENDANCE_EVENTS,String.valueOf(e.attendanceId()),e);}
    public void corrected(AttendanceCorrectedEvent e){kafka.send(KafkaTopicConstants.ATTENDANCE_EVENTS,String.valueOf(e.attendanceId()),e);}
}
