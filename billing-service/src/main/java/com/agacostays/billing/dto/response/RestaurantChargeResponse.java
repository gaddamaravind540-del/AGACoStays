package com.agacostays.billing.dto.response; import java.math.BigDecimal; public record RestaurantChargeResponse(Long orderId,Long bookingId,BigDecimal amount,String orderStatus){}
