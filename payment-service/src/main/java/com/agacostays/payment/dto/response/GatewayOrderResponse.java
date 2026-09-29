package com.agacostays.payment.dto.response;

import java.math.BigDecimal;

public record GatewayOrderResponse(String gatewayOrderId, BigDecimal amount, String currency, String gatewayName) {}
