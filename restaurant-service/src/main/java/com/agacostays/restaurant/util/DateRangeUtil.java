package com.agacostays.restaurant.util;
import java.time.LocalDate;
public final class DateRangeUtil {
    private DateRangeUtil() {}
    public static boolean valid(LocalDate start, LocalDate end){ return start!=null && end!=null && !end.isBefore(start); }
}
