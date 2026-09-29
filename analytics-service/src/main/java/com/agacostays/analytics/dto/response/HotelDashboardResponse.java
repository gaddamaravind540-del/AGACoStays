package com.agacostays.analytics.dto.response;
import java.math.BigDecimal;
import java.util.List;
public record HotelDashboardResponse(Long branchId,BigDecimal totalRevenue,BigDecimal roomRevenue,
BigDecimal restaurantRevenue,BigDecimal occupancyRate,long bookings,long customers,double averageHotelRating,
List<KpiResponse> kpis,List<ChartDataResponse> revenueChart){}
