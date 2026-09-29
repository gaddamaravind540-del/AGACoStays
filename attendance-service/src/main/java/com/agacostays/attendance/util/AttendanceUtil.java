package com.agacostays.attendance.util;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalTime;
public final class AttendanceUtil {
    private AttendanceUtil(){}
    public static BigDecimal workedHours(LocalTime in,LocalTime out){
        if(in==null||out==null||out.isBefore(in)) return BigDecimal.ZERO;
        long mins=Duration.between(in,out).toMinutes();
        return BigDecimal.valueOf(mins).divide(BigDecimal.valueOf(60),2,RoundingMode.HALF_UP);
    }
}
