package com.agacostays.analytics.service;
import java.math.BigDecimal;
public interface AnalyticsProjectionService {
    void bookingEvent(Long branchId,BigDecimal roomRevenue,Long customerId);
    void paymentEvent(Long branchId,BigDecimal amount);
    void restaurantEvent(Long branchId,BigDecimal amount,boolean delivered);
    void attendanceEvent(Long branchId,String status);
    void payrollEvent(Long branchId,BigDecimal gross,BigDecimal net,boolean paid);
    void feedbackEvent(Long branchId,double hotelRating,double restaurantRating,boolean restaurant);
}
