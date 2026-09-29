package com.agacostays.analytics.dto.request;
import java.time.LocalDate;
public record BranchRevenueRequest(Long branchId,LocalDate fromDate,LocalDate toDate){}
