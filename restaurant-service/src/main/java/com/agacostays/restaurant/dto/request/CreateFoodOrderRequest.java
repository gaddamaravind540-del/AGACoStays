package com.agacostays.restaurant.dto.request;

import com.agacostays.restaurant.enums.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class CreateFoodOrderRequest {
    @NotNull private Long bookingId;
    private Long roomId;
    @NotEmpty @Valid private List<OrderItemRequest> items;
    @NotNull private DeliveryType deliveryType;
    @NotNull private RestaurantPaymentOption paymentMode;

    @Data
    public static class OrderItemRequest {
        @NotNull private Long menuItemId;
        @NotNull @Min(1) private Integer quantity;
    }
}
