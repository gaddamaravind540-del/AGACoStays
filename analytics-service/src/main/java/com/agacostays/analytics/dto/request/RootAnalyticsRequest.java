package com.agacostays.analytics.dto.request;
import java.time.LocalDate;
public record RootAnalyticsRequest(LocalDate fromDate,LocalDate toDate,String metric){}
