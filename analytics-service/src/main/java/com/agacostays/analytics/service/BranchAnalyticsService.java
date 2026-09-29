package com.agacostays.analytics.service;
import com.agacostays.analytics.dto.response.*;
public interface BranchAnalyticsService {
    HotelDashboardResponse branchHotel(Long branchId);
    RestaurantDashboardResponse branchRestaurant(Long branchId);
    BranchRevenueResponse overallRevenue(Long branchId);
}
