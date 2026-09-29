package com.agacostays.restaurant.service.impl;
import com.agacostays.restaurant.dto.response.ServingOrderResponse;
import com.agacostays.restaurant.enums.FoodOrderStatus;
import com.agacostays.restaurant.exception.ResourceNotFoundException;
import com.agacostays.restaurant.repository.RestaurantOrderRepository;
import com.agacostays.restaurant.producer.RestaurantEventProducer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServingServiceImpl implements com.agacostays.restaurant.service.ServingService {
    private final RestaurantOrderRepository repo; private final RestaurantEventProducer producer;
    public ServingServiceImpl(RestaurantOrderRepository repo, RestaurantEventProducer producer){this.repo=repo;this.producer=producer;}
    @Override @Transactional public ServingOrderResponse pickedUp(Long orderId){
        var o=repo.findById(orderId).orElseThrow(()->new ResourceNotFoundException("Order not found"));
        o.setOrderStatus(FoodOrderStatus.OUT_FOR_DELIVERY); repo.save(o); return new ServingOrderResponse(orderId,o.getOrderStatus());
    }
    @Override @Transactional public ServingOrderResponse delivered(Long orderId){
        var o=repo.findById(orderId).orElseThrow(()->new ResourceNotFoundException("Order not found"));
        o.setOrderStatus(FoodOrderStatus.DELIVERED); repo.save(o); producer.delivered(o); return new ServingOrderResponse(orderId,o.getOrderStatus());
    }
}
