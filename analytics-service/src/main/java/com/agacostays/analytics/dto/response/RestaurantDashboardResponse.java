package com.agacostays.analytics.dto.response;
import java.math.BigDecimal;
import java.util.List;
public record RestaurantDashboardResponse(Long branchId,BigDecimal totalSales,long orders,long deliveredOrders,
double averageRating,List<KpiResponse> kpis,List<ChartDataResponse> salesChart){}
