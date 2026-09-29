package com.agacostays.restaurant.service.impl;

import com.agacostays.restaurant.client.BookingServiceClient;
import com.agacostays.restaurant.dto.request.CreateFoodOrderRequest;
import com.agacostays.restaurant.dto.response.*;
import com.agacostays.restaurant.entity.*;
import com.agacostays.restaurant.enums.*;
import com.agacostays.restaurant.exception.*;
import com.agacostays.restaurant.mapper.RestaurantOrderMapper;
import com.agacostays.restaurant.repository.*;
import com.agacostays.restaurant.producer.RestaurantEventProducer;
import com.agacostays.restaurant.security.CurrentUserProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class FoodOrderServiceImpl implements com.agacostays.restaurant.service.FoodOrderService {
    private final RestaurantOrderRepository orderRepo;
    private final RestaurantOrderItemRepository itemRepo;
    private final RestaurantMenuRepository menuRepo;
    private final RestaurantRepository restaurantRepo;
    private final BookingServiceClient bookingClient;
    private final RestaurantOrderMapper mapper;
    private final RestaurantEventProducer producer;
    private final CurrentUserProvider currentUser;

    public FoodOrderServiceImpl(RestaurantOrderRepository orderRepo, RestaurantOrderItemRepository itemRepo,
                                RestaurantMenuRepository menuRepo, RestaurantRepository restaurantRepo,
                                BookingServiceClient bookingClient, RestaurantOrderMapper mapper,
                                RestaurantEventProducer producer, CurrentUserProvider currentUser) {
        this.orderRepo=orderRepo; this.itemRepo=itemRepo; this.menuRepo=menuRepo; this.restaurantRepo=restaurantRepo;
        this.bookingClient=bookingClient; this.mapper=mapper; this.producer=producer; this.currentUser=currentUser;
    }

    @Override @Transactional
    public FoodOrderResponse create(Long branchId, CreateFoodOrderRequest req) {
        var booking;
        try {
            booking = bookingClient.getBooking(req.getBookingId());
        } catch (Exception ex) {
            throw new BusinessRuleException("Booking could not be validated");
        }
        if (booking.bookingId()==null || booking.customerId()==null)
            throw new BusinessRuleException("Invalid booking response");
        if (!"APPROVED".equalsIgnoreCase(booking.status()) && !"CHECKED_IN".equalsIgnoreCase(booking.status()))
            throw new BusinessRuleException("Food order requires an approved or checked-in booking");

        var restaurant = restaurantRepo.findByBranchId(branchId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

        RestaurantOrder order = RestaurantOrder.builder()
                .branchId(branchId).restaurantId(restaurant.getRestaurantId()).bookingId(req.getBookingId())
                .roomId(req.getRoomId()).customerId(booking.customerId())
                .totalAmount(BigDecimal.ZERO).orderStatus(FoodOrderStatus.PLACED)
                .deliveryType(req.getDeliveryType()).paymentMode(req.getPaymentMode())
                .createdBy(currentUser.userId()).updatedBy(currentUser.userId()).build();

        order = orderRepo.save(order);

        BigDecimal total = BigDecimal.ZERO;
        for (var requestItem : req.getItems()) {
            RestaurantMenu menu = menuRepo.findById(requestItem.getMenuItemId())
                    .orElseThrow(() -> new ResourceNotFoundException("Menu item not found: " + requestItem.getMenuItemId()));
            if (!menu.getBranchId().equals(branchId) || !menu.isAvailability() || menu.getStatus()!=MenuItemStatus.AVAILABLE)
                throw new BusinessRuleException("Menu item is not available: " + menu.getItemName());
            BigDecimal line = menu.getPrice().multiply(BigDecimal.valueOf(requestItem.getQuantity()));
            total = total.add(line);
            itemRepo.save(RestaurantOrderItem.builder().orderId(order.getOrderId())
                    .menuItemId(menu.getMenuItemId()).itemName(menu.getItemName())
                    .quantity(requestItem.getQuantity()).unitPrice(menu.getPrice()).lineTotal(line).build());
        }
        order.setTotalAmount(total);
        order = orderRepo.save(order);
        producer.placed(order);
        return toResponse(order);
    }

    @Override @Transactional(readOnly=true)
    public FoodOrderResponse get(Long orderId) {
        RestaurantOrder o = orderRepo.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        return toResponse(o);
    }

    @Override @Transactional(readOnly=true)
    public List<FoodOrderResponse> myOrders() {
        Long customerId = currentUser.userId();
        return orderRepo.findByCustomerIdOrderByCreatedAtDesc(customerId).stream().map(this::toResponse).toList();
    }

    @Override @Transactional
    public FoodOrderResponse updateStatus(Long orderId, FoodOrderStatus status) {
        RestaurantOrder o = orderRepo.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        o.setOrderStatus(status);
        o.setUpdatedBy(currentUser.userId());
        o = orderRepo.save(o);

        switch (status) {
            case ACCEPTED -> producer.accepted(o);
            case REJECTED -> producer.rejected(o);
            case READY_FOR_DELIVERY -> producer.ready(o);
            case DELIVERED -> producer.delivered(o);
            case CANCELLED -> producer.cancelled(o);
            default -> {}
        }
        return toResponse(o);
    }

    private FoodOrderResponse toResponse(RestaurantOrder o) {
        var items = itemRepo.findByOrderId(o.getOrderId()).stream().map(mapper::item).toList();
        return mapper.toResponse(o, items);
    }
}
