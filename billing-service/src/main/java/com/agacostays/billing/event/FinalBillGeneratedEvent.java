package com.agacostays.billing.event;public record FinalBillGeneratedEvent(Long billId,Long bookingId,Long branchId,java.math.BigDecimal finalAmount){}
