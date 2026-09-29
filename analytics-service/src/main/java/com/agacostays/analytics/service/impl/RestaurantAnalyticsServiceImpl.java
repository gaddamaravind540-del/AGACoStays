package com.agacostays.analytics.service.impl;

import com.agacostays.analytics.dto.response.*;
import com.agacostays.analytics.entity.RestaurantSalesProjection;
import com.agacostays.analytics.repository.RestaurantSalesProjectionRepository;
import com.agacostays.analytics.repository.FeedbackProjectionRepository;
import com.agacostays.analytics.entity.FeedbackProjection;
import com.agacostays.analytics.service.RestaurantAnalyticsService;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
public class RestaurantAnalyticsServiceImpl implements RestaurantAnalyticsService {
    private final RestaurantSalesProjectionRepository repo;
    private final FeedbackProjectionRepository feedback;
    public RestaurantAnalyticsServiceImpl(RestaurantSalesProjectionRepository repo,FeedbackProjectionRepository feedback){
        this.repo=repo;this.feedback=feedback;
    }
    public RestaurantDashboardResponse dashboard(Long branchId){
        LocalDate to=LocalDate.now(),from=to.minusDays(30);
        List<RestaurantSalesProjection> rows=branchId==null?repo.findByMetricDateBetween(from,to):repo.findByBranchIdAndMetricDateBetween(branchId,from,to);
        BigDecimal sales=rows.stream().map(RestaurantSalesProjection::getSalesAmount).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add);
        long orders=rows.stream().mapToLong(x->x.getOrderCount()==null?0:x.getOrderCount()).sum();
        long delivered=rows.stream().mapToLong(x->x.getDeliveredCount()==null?0:x.getDeliveredCount()).sum();
        double rating=0;
        var frows=branchId==null?feedback.findByMetricDateBetween(from,to):feedback.findByBranchIdAndMetricDateBetween(branchId,from,to);
        BigDecimal rsum=frows.stream().map(FeedbackProjection::getRestaurantRatingSum).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add);
        long rc=frows.stream().mapToLong(x->x.getRestaurantFeedbackCount()==null?0:x.getRestaurantFeedbackCount()).sum();
        if(rc>0)rating=rsum.doubleValue()/rc;
        List<ChartDataResponse> chart=rows.stream().sorted(Comparator.comparing(RestaurantSalesProjection::getMetricDate))
            .map(x->new ChartDataResponse(x.getMetricDate().toString(),x.getSalesAmount())).toList();
        List<KpiResponse> kpis=List.of(new KpiResponse("Sales",sales,"30-day projection"),
            new KpiResponse("Orders",orders,"30-day projection"),new KpiResponse("Delivered",delivered,"30-day projection"));
        return new RestaurantDashboardResponse(branchId,sales,orders,delivered,rating,kpis,chart);
    }
}
