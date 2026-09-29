package com.agacostays.payroll.event; public record PayrollGeneratedEvent(Long payrollId,Long branchId,Long staffId,Integer month,Integer year,java.math.BigDecimal netSalary){}
