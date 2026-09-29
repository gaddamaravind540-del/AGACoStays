package com.agacostays.billing.dto.response; import java.math.BigDecimal; public record BookingResponse(Long bookingId,Long branchId,Long customerId,BigDecimal roomCharges,String bookingStatus){}
