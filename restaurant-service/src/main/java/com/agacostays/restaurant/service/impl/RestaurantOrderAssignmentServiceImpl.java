package com.agacostays.restaurant.service.impl;
import com.agacostays.restaurant.entity.RestaurantOrderAssignment;
import com.agacostays.restaurant.exception.ResourceNotFoundException;
import com.agacostays.restaurant.repository.RestaurantOrderAssignmentRepository;
import com.agacostays.restaurant.repository.RestaurantOrderRepository;
import com.agacostays.restaurant.security.CurrentUserProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RestaurantOrderAssignmentServiceImpl implements com.agacostays.restaurant.service.RestaurantOrderAssignmentService {
    private final RestaurantOrderAssignmentRepository repo; private final RestaurantOrderRepository orderRepo;
    private final CurrentUserProvider currentUser;
    public RestaurantOrderAssignmentServiceImpl(RestaurantOrderAssignmentRepository repo, RestaurantOrderRepository orderRepo, CurrentUserProvider currentUser){
        this.repo=repo;this.orderRepo=orderRepo;this.currentUser=currentUser;
    }
    @Override @Transactional public void assignChef(Long orderId, Long chefId){
        orderRepo.findById(orderId).orElseThrow(()->new ResourceNotFoundException("Order not found"));
        var a=repo.findByOrderId(orderId).orElseGet(()->RestaurantOrderAssignment.builder().orderId(orderId).build());
        a.setChefId(chefId);a.setAssignedBy(currentUser.userId());repo.save(a);
    }
    @Override @Transactional public void assignServingStaff(Long orderId, Long staffId){
        orderRepo.findById(orderId).orElseThrow(()->new ResourceNotFoundException("Order not found"));
        var a=repo.findByOrderId(orderId).orElseGet(()->RestaurantOrderAssignment.builder().orderId(orderId).build());
        a.setServingStaffId(staffId);a.setAssignedBy(currentUser.userId());repo.save(a);
    }
}
