package com.agacostays.restaurant.dto.response;

import com.agacostays.restaurant.enums.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class FoodOrderResponse {
    private Long orderId;
    private Long branchId;
    private Long restaurantId;
    private Long bookingId;
    private Long roomId;
    private Long customerId;
    private BigDecimal totalAmount;
    private FoodOrderStatus orderStatus;
    private DeliveryType deliveryType;
    private RestaurantPaymentOption paymentMode;
    private OffsetDateTime createdAt;
    private List<FoodOrderItemResponse> items;
}
