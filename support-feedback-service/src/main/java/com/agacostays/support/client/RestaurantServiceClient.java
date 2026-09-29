package com.agacostays.support.client;

import com.agacostays.support.dto.response.RestaurantOrderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="restaurant-service", url="${services.restaurant.url}")
public interface RestaurantServiceClient {
    @GetMapping("/api/restaurant/orders/{orderId}")
    RestaurantOrderResponse getOrder(@PathVariable Long orderId);
}
