package com.agacostays.restaurant.mapper;
import com.agacostays.restaurant.entity.RestaurantOrderItem;
import com.agacostays.restaurant.dto.response.FoodOrderItemResponse;
import org.springframework.stereotype.Component;
@Component
public class RestaurantOrderItemMapper {
    public FoodOrderItemResponse toResponse(RestaurantOrderItem i) {
        return FoodOrderItemResponse.builder().orderItemId(i.getOrderItemId()).menuItemId(i.getMenuItemId())
            .itemName(i.getItemName()).quantity(i.getQuantity()).unitPrice(i.getUnitPrice()).lineTotal(i.getLineTotal()).build();
    }
}
