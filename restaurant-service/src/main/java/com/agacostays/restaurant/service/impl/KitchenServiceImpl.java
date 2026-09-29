package com.agacostays.restaurant.service.impl;
import com.agacostays.restaurant.dto.response.KitchenOrderResponse;
import com.agacostays.restaurant.enums.FoodOrderStatus;
import com.agacostays.restaurant.exception.ResourceNotFoundException;
import com.agacostays.restaurant.repository.RestaurantOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class KitchenServiceImpl implements com.agacostays.restaurant.service.KitchenService {
    private final RestaurantOrderRepository repo;
    public KitchenServiceImpl(RestaurantOrderRepository repo){this.repo=repo;}
    @Override @Transactional public KitchenOrderResponse preparing(Long orderId){
        var o=repo.findById(orderId).orElseThrow(()->new ResourceNotFoundException("Order not found"));
        o.setOrderStatus(FoodOrderStatus.PREPARING); repo.save(o); return new KitchenOrderResponse(orderId,o.getOrderStatus());
    }
    @Override @Transactional public KitchenOrderResponse ready(Long orderId){
        var o=repo.findById(orderId).orElseThrow(()->new ResourceNotFoundException("Order not found"));
        o.setOrderStatus(FoodOrderStatus.READY_FOR_DELIVERY); repo.save(o); return new KitchenOrderResponse(orderId,o.getOrderStatus());
    }
}
