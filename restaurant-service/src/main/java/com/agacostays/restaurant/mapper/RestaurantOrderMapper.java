package com.agacostays.restaurant.mapper;

import com.agacostays.restaurant.dto.response.*;
import com.agacostays.restaurant.entity.*;
import org.springframework.stereotype.Component;

@Component
public class RestaurantOrderMapper {
    public FoodOrderResponse toResponse(RestaurantOrder o, java.util.List<FoodOrderItemResponse> items) {
        return FoodOrderResponse.builder()
                .orderId(o.getOrderId()).branchId(o.getBranchId()).restaurantId(o.getRestaurantId())
                .bookingId(o.getBookingId()).roomId(o.getRoomId()).customerId(o.getCustomerId())
                .totalAmount(o.getTotalAmount()).orderStatus(o.getOrderStatus())
                .deliveryType(o.getDeliveryType()).paymentMode(o.getPaymentMode())
                .createdAt(o.getCreatedAt()).items(items).build();
    }
    public FoodOrderItemResponse item(RestaurantOrderItem i) {
        return FoodOrderItemResponse.builder()
                .orderItemId(i.getOrderItemId()).menuItemId(i.getMenuItemId()).itemName(i.getItemName())
                .quantity(i.getQuantity()).unitPrice(i.getUnitPrice()).lineTotal(i.getLineTotal()).build();
    }
}
