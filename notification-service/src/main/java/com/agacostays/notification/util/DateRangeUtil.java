package com.agacostays.notification.util;

import java.time.LocalDateTime;

public final class DateRangeUtil {
    private DateRangeUtil() {}
    public static boolean due(LocalDateTime scheduled, LocalDateTime now){ return scheduled!=null && !scheduled.isAfter(now); }
}
