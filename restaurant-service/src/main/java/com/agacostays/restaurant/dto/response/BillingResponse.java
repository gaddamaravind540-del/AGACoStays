package com.agacostays.restaurant.dto.response;

public record BillingResponse(Long orderId, String status, java.math.BigDecimal totalAmount) {}
