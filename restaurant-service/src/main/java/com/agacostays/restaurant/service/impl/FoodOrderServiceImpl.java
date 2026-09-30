package com.agacostays.restaurant.service.impl;

import com.agacostays.restaurant.client.BookingServiceClient;
import com.agacostays.restaurant.dto.request.CreateFoodOrderRequest;
import com.agacostays.restaurant.dto.response.BookingResponse;
import com.agacostays.restaurant.dto.response.FoodOrderResponse;
import com.agacostays.restaurant.entity.Restaurant;
import com.agacostays.restaurant.entity.RestaurantMenu;
import com.agacostays.restaurant.entity.RestaurantOrder;
import com.agacostays.restaurant.entity.RestaurantOrderItem;
import com.agacostays.restaurant.enums.FoodOrderStatus;
import com.agacostays.restaurant.enums.MenuItemStatus;
import com.agacostays.restaurant.exception.BusinessRuleException;
import com.agacostays.restaurant.exception.ResourceNotFoundException;
import com.agacostays.restaurant.mapper.RestaurantOrderMapper;
import com.agacostays.restaurant.producer.RestaurantEventProducer;
import com.agacostays.restaurant.repository.RestaurantMenuRepository;
import com.agacostays.restaurant.repository.RestaurantOrderItemRepository;
import com.agacostays.restaurant.repository.RestaurantOrderRepository;
import com.agacostays.restaurant.repository.RestaurantRepository;
import com.agacostays.restaurant.security.CurrentUserProvider;
import com.agacostays.restaurant.service.FoodOrderService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class FoodOrderServiceImpl implements FoodOrderService {

    private final RestaurantOrderRepository orderRepo;
    private final RestaurantOrderItemRepository itemRepo;
    private final RestaurantMenuRepository menuRepo;
    private final RestaurantRepository restaurantRepo;
    private final BookingServiceClient bookingClient;
    private final RestaurantOrderMapper mapper;
    private final RestaurantEventProducer producer;
    private final CurrentUserProvider currentUser;

    public FoodOrderServiceImpl(
            RestaurantOrderRepository orderRepo,
            RestaurantOrderItemRepository itemRepo,
            RestaurantMenuRepository menuRepo,
            RestaurantRepository restaurantRepo,
            BookingServiceClient bookingClient,
            RestaurantOrderMapper mapper,
            RestaurantEventProducer producer,
            CurrentUserProvider currentUser) {

        this.orderRepo = orderRepo;
        this.itemRepo = itemRepo;
        this.menuRepo = menuRepo;
        this.restaurantRepo = restaurantRepo;
        this.bookingClient = bookingClient;
        this.mapper = mapper;
        this.producer = producer;
        this.currentUser = currentUser;
    }

    @Override
    @Transactional
    public FoodOrderResponse create(
            Long branchId,
            CreateFoodOrderRequest req) {

        BookingResponse booking;

        try {
            booking = bookingClient.getBooking(
                    req.getBookingId()
            );
        } catch (Exception ex) {
            throw new BusinessRuleException(
                    "Booking could not be validated"
            );
        }

        if (booking.bookingId() == null
                || booking.customerId() == null) {

            throw new BusinessRuleException(
                    "Invalid booking response"
            );
        }

        if (!"APPROVED".equalsIgnoreCase(booking.status())
                && !"CHECKED_IN".equalsIgnoreCase(booking.status())) {

            throw new BusinessRuleException(
                    "Food order requires an approved or checked-in booking"
            );
        }

        Restaurant restaurant = restaurantRepo
                .findByBranchId(branchId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Restaurant not found"
                        ));

        RestaurantOrder order = RestaurantOrder.builder()
                .branchId(branchId)
                .restaurantId(restaurant.getRestaurantId())
                .bookingId(req.getBookingId())
                .roomId(req.getRoomId())
                .customerId(booking.customerId())
                .totalAmount(BigDecimal.ZERO)
                .orderStatus(FoodOrderStatus.PLACED)
                .deliveryType(req.getDeliveryType())
                .paymentMode(req.getPaymentMode())
                .createdBy(currentUser.userId())
                .updatedBy(currentUser.userId())
                .build();

        order = orderRepo.save(order);

        BigDecimal total = BigDecimal.ZERO;

        for (var requestItem : req.getItems()) {

            RestaurantMenu menu = menuRepo.findById(
                    requestItem.getMenuItemId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Menu item not found: "
                                            + requestItem.getMenuItemId()
                            ));

            if (!menu.getBranchId().equals(branchId)
                    || !menu.isAvailability()
                    || menu.getStatus() != MenuItemStatus.AVAILABLE) {

                throw new BusinessRuleException(
                        "Menu item is not available: "
                                + menu.getItemName()
                );
            }

            BigDecimal lineTotal = menu.getPrice()
                    .multiply(
                            BigDecimal.valueOf(
                                    requestItem.getQuantity()));

            total = total.add(lineTotal);

            itemRepo.save(
                    RestaurantOrderItem.builder()
                            .orderId(order.getOrderId())
                            .menuItemId(menu.getMenuItemId())
                            .itemName(menu.getItemName())
                            .quantity(requestItem.getQuantity())
                            .unitPrice(menu.getPrice())
                            .lineTotal(lineTotal)
                            .build()
            );
        }

        order.setTotalAmount(total);

        order = orderRepo.save(order);

        producer.placed(order);

        return toResponse(order);
    }

    @Override
    @Transactional(readOnly = true)
    public FoodOrderResponse get(Long orderId) {

        RestaurantOrder order = orderRepo.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order not found"
                        ));

        return toResponse(order);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FoodOrderResponse> myOrders() {

        Long customerId = currentUser.userId();

        return orderRepo
                .findByCustomerIdOrderByCreatedAtDesc(customerId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public FoodOrderResponse updateStatus(
            Long orderId,
            FoodOrderStatus status) {

        RestaurantOrder order = orderRepo.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order not found"
                        ));

        order.setOrderStatus(status);
        order.setUpdatedBy(currentUser.userId());

        order = orderRepo.save(order);

        switch (status) {

            case ACCEPTED ->
                    producer.accepted(order);

            case REJECTED ->
                    producer.rejected(order);

            case READY_FOR_DELIVERY ->
                    producer.ready(order);

            case DELIVERED ->
                    producer.delivered(order);

            case CANCELLED ->
                    producer.cancelled(order);

            default -> {
            }
        }

        return toResponse(order);
    }

    private FoodOrderResponse toResponse(
            RestaurantOrder order) {

        var items = itemRepo.findByOrderId(
                        order.getOrderId())
                .stream()
                .map(mapper::item)
                .toList();

        return mapper.toResponse(order, items);
    }
}