package com.agacostays.analytics.dto.request;
import com.agacostays.analytics.enums.ReportFormat;
import com.agacostays.analytics.enums.ReportType;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
public record ExportReportRequest(@NotNull ReportType reportType,@NotNull ReportFormat format,
                                  Long branchId,LocalDate fromDate,LocalDate toDate){}
