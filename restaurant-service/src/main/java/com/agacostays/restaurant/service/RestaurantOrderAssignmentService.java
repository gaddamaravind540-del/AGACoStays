package com.agacostays.restaurant.service;

public interface RestaurantOrderAssignmentService {
    void assignChef(Long orderId, Long chefId);
    void assignServingStaff(Long orderId, Long servingStaffId);
}
