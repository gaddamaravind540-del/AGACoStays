package com.agacostays.support.service.impl;

import com.agacostays.support.client.RestaurantServiceClient;
import com.agacostays.support.dto.request.RestaurantFeedbackRequest;
import com.agacostays.support.dto.response.RestaurantFeedbackResponse;
import com.agacostays.support.dto.response.RestaurantOrderResponse;
import com.agacostays.support.entity.RestaurantFeedback;
import com.agacostays.support.exception.AccessDeniedException;
import com.agacostays.support.exception.BusinessRuleException;
import com.agacostays.support.exception.DuplicateResourceException;
import com.agacostays.support.mapper.RestaurantFeedbackMapper;
import com.agacostays.support.producer.SupportFeedbackEventProducer;
import com.agacostays.support.repository.RestaurantFeedbackRepository;
import com.agacostays.support.security.CurrentUserProvider;
import com.agacostays.support.service.RestaurantFeedbackService;
import com.agacostays.support.validation.SupportFeedbackValidationService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RestaurantFeedbackServiceImpl
        implements RestaurantFeedbackService {

    private final RestaurantFeedbackRepository repo;
    private final RestaurantServiceClient restaurantClient;
    private final RestaurantFeedbackMapper mapper;
    private final CurrentUserProvider currentUser;
    private final SupportFeedbackEventProducer producer;
    private final SupportFeedbackValidationService validation;

    public RestaurantFeedbackServiceImpl(
            RestaurantFeedbackRepository repo,
            RestaurantServiceClient restaurantClient,
            RestaurantFeedbackMapper mapper,
            CurrentUserProvider currentUser,
            SupportFeedbackEventProducer producer,
            SupportFeedbackValidationService validation) {

        this.repo = repo;
        this.restaurantClient = restaurantClient;
        this.mapper = mapper;
        this.currentUser = currentUser;
        this.producer = producer;
        this.validation = validation;
    }

    @Override
    @Transactional
    public RestaurantFeedbackResponse create(
            RestaurantFeedbackRequest req) {

        Long customerId = currentUser.userId();

        RestaurantOrderResponse order;

        try {

            order = restaurantClient.getOrder(
                    req.getOrderId()
            );

        } catch (Exception e) {

            throw new BusinessRuleException(
                    "Restaurant order could not be validated"
            );
        }

        if (order.customerId() == null
                || !customerId.equals(order.customerId())) {

            throw new AccessDeniedException(
                    "Order does not belong to current customer"
            );
        }

        if (!"DELIVERED".equalsIgnoreCase(
                order.status())) {

            throw new BusinessRuleException(
                    "Restaurant feedback requires a delivered order"
            );
        }

        if (repo.findByOrderIdAndCustomerId(
                req.getOrderId(),
                customerId
        ).isPresent()) {

            throw new DuplicateResourceException(
                    "Restaurant feedback already exists for this order"
            );
        }

        validation.rating(
                req.getFoodRating(),
                "foodRating"
        );

        validation.rating(
                req.getTasteRating(),
                "tasteRating"
        );

        validation.rating(
                req.getDeliveryRating(),
                "deliveryRating"
        );

        RestaurantFeedback feedback =
                RestaurantFeedback.builder()
                        .branchId(order.branchId())
                        .bookingId(order.bookingId())
                        .orderId(req.getOrderId())
                        .customerId(customerId)
                        .foodRating(req.getFoodRating())
                        .tasteRating(req.getTasteRating())
                        .deliveryRating(req.getDeliveryRating())
                        .comments(req.getComments())
                        .build();

        RestaurantFeedback saved =
                repo.save(feedback);

        producer.restaurantFeedbackSubmitted(saved);

        return mapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RestaurantFeedbackResponse> byMyOrders() {

        return repo.findByCustomerIdOrderByCreatedAtDesc(
                        currentUser.userId())
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<RestaurantFeedbackResponse> byBranch(
            Long branchId) {

        return repo.findByBranchIdOrderByCreatedAtDesc(
                        branchId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<RestaurantFeedbackResponse> byOrder(
            Long orderId) {

        return repo.findByOrderIdOrderByCreatedAtDesc(
                        orderId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }
}