package com.agacostays.analytics.dto.request;
import com.agacostays.analytics.enums.AggregationType;
import com.agacostays.analytics.enums.MetricType;
import java.time.LocalDate;
public record AnalyticsFilterRequest(Long branchId,MetricType metric,AggregationType aggregation,
                                     LocalDate fromDate,LocalDate toDate){}
