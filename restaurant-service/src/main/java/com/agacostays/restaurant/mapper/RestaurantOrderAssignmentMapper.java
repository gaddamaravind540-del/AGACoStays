package com.agacostays.restaurant.mapper;
import com.agacostays.restaurant.entity.RestaurantOrderAssignment;
import org.springframework.stereotype.Component;
@Component
public class RestaurantOrderAssignmentMapper {
    public String toDescription(RestaurantOrderAssignment a) {
        return "chefId=" + a.getChefId() + ", servingStaffId=" + a.getServingStaffId();
    }
}
