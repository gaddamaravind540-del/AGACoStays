package com.agacostays.analytics.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record ManagerDashboardResponse(BigDecimal totalRevenue, long totalBookings, double occupancyRate,
		long attendancePresent, long attendanceAbsent, BigDecimal payrollNet,
		List<BranchRevenueResponse> branchRevenue) {
}
