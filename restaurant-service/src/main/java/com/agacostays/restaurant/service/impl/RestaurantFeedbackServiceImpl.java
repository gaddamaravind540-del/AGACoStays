package com.agacostays.restaurant.service.impl;
import com.agacostays.restaurant.client.BookingServiceClient;
import com.agacostays.restaurant.dto.request.RestaurantFeedbackRequest;
import com.agacostays.restaurant.dto.response.RestaurantFeedbackResponse;
import com.agacostays.restaurant.entity.RestaurantFeedback;
import com.agacostays.restaurant.exception.BusinessRuleException;
import com.agacostays.restaurant.mapper.RestaurantFeedbackMapper;
import com.agacostays.restaurant.repository.RestaurantFeedbackRepository;
import com.agacostays.restaurant.repository.RestaurantOrderRepository;
import com.agacostays.restaurant.security.CurrentUserProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class RestaurantFeedbackServiceImpl implements com.agacostays.restaurant.service.RestaurantFeedbackService {
    private final RestaurantFeedbackRepository feedbackRepo; private final RestaurantOrderRepository orderRepo;
    private final RestaurantFeedbackMapper mapper; private final CurrentUserProvider currentUser;
    public RestaurantFeedbackServiceImpl(RestaurantFeedbackRepository feedbackRepo, RestaurantOrderRepository orderRepo,
                                         RestaurantFeedbackMapper mapper, CurrentUserProvider currentUser){
        this.feedbackRepo=feedbackRepo;this.orderRepo=orderRepo;this.mapper=mapper;this.currentUser=currentUser;
    }
    @Override @Transactional
    public RestaurantFeedbackResponse create(Long branchId, RestaurantFeedbackRequest req){
        var order=orderRepo.findById(req.getOrderId()).orElseThrow(()->new BusinessRuleException("Order not found"));
        if(!order.getBranchId().equals(branchId) || !order.getCustomerId().equals(currentUser.userId()))
            throw new BusinessRuleException("Order does not belong to current customer/branch");
        if(order.getOrderStatus()!=com.agacostays.restaurant.enums.FoodOrderStatus.DELIVERED)
            throw new BusinessRuleException("Feedback requires a delivered order");
        RestaurantFeedback f=RestaurantFeedback.builder().branchId(branchId).restaurantId(order.getRestaurantId())
            .orderId(order.getOrderId()).customerId(currentUser.userId()).rating(req.getRating()).comments(req.getComments()).build();
        return mapper.toResponse(feedbackRepo.save(f));
    }
    @Override @Transactional(readOnly=true)
    public List<RestaurantFeedbackResponse> byBranch(Long branchId){
        return feedbackRepo.findByRestaurantIdOrderByCreatedAtDesc(branchId).stream().map(mapper::toResponse).toList();
    }
}
