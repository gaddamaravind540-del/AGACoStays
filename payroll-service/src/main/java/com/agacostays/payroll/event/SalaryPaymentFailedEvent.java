package com.agacostays.payroll.event; public record SalaryPaymentFailedEvent(Long payrollId,Long staffId,java.math.BigDecimal amount,String reason){}
