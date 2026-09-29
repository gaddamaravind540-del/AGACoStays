package com.agacostays.analytics.service.impl;

import com.agacostays.analytics.dto.response.*;
import com.agacostays.analytics.repository.HotelRevenueProjectionRepository;
import com.agacostays.analytics.service.*;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class BranchAnalyticsServiceImpl implements BranchAnalyticsService {
    private final HotelAnalyticsService hotel;
    private final RestaurantAnalyticsService restaurant;
    private final HotelRevenueProjectionRepository revenueRepo;
    public BranchAnalyticsServiceImpl(HotelAnalyticsService hotel,RestaurantAnalyticsService restaurant,
                                      HotelRevenueProjectionRepository revenueRepo){
        this.hotel=hotel;this.restaurant=restaurant;this.revenueRepo=revenueRepo;
    }
    public HotelDashboardResponse branchHotel(Long branchId){return hotel.dashboard(branchId);}
    public RestaurantDashboardResponse branchRestaurant(Long branchId){return restaurant.dashboard(branchId);}
    public BranchRevenueResponse overallRevenue(Long branchId){
        LocalDate to=LocalDate.now(),from=to.minusDays(30);
        BigDecimal total=revenueRepo.findByBranchIdAndMetricDateBetween(branchId,from,to).stream()
            .map(x->x.getTotalRevenue()==null?BigDecimal.ZERO:x.getTotalRevenue()).reduce(BigDecimal.ZERO,BigDecimal::add);
        return new BranchRevenueResponse(branchId,total,"LAST_30_DAYS");
    }
}
