package com.agacostays.payment.util;
import java.time.*; public final class DateRangeUtil { private DateRangeUtil(){} public static Instant minutesAgo(long minutes){return Instant.now().minusSeconds(minutes*60);}}
