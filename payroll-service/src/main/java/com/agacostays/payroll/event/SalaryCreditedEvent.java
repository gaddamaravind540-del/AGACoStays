package com.agacostays.payroll.event; public record SalaryCreditedEvent(Long payrollId,Long staffId,java.math.BigDecimal amount,String transactionId){}
