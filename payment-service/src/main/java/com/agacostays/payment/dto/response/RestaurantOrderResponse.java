package com.agacostays.payment.dto.response;

import java.math.BigDecimal;

public record RestaurantOrderResponse(String orderId, Long branchId, Long customerId, BigDecimal amount, String paymentStatus, String orderStatus) {}
