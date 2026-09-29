package com.agacostays.attendance.util;
import java.time.LocalDate;
public final class DateRangeUtil {
    private DateRangeUtil(){}
    public static LocalDate firstDay(int year,int month){return LocalDate.of(year,month,1);}
    public static LocalDate lastDay(int year,int month){return firstDay(year,month).withDayOfMonth(firstDay(year,month).lengthOfMonth());}
}
