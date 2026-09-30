package com.agacostays.analytics.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record RootDashboardResponse(BigDecimal totalRevenue, long totalBranches, long totalBookings,
		long totalCustomers, List<BranchRevenueResponse> branchRevenue) {
}
