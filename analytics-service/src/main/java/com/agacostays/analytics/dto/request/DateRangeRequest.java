package com.agacostays.analytics.dto.request;
import com.agacostays.analytics.enums.DateRangeType;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
public record DateRangeRequest(@NotNull DateRangeType rangeType,LocalDate fromDate,LocalDate toDate){}
