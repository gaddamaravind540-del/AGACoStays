package com.agacostays.billing.event;public record BillPaidEvent(Long billId,Long bookingId,Long customerId,java.math.BigDecimal amount){}
