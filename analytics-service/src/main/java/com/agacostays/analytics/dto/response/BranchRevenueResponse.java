package com.agacostays.analytics.dto.response;
import java.math.BigDecimal;
public record BranchRevenueResponse(Long branchId,BigDecimal revenue,String period){}
