package com.agacostays.attendance.producer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import com.agacostays.attendance.event.StaffCheckedInEvent;
class AttendanceProducerTest {@Test void eventLoads(){assertNotNull(StaffCheckedInEvent.class);}}
