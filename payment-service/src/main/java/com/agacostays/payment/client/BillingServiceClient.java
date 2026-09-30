package com.agacostays.payment.client;

import com.agacostays.payment.dto.response.BillingResponse;
import com.agacostays.payment.exception.BusinessRuleException;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;

@Component
public class BillingServiceClient {

    private final RestClient client;

    public BillingServiceClient(
            RestClient.Builder builder,
            @Value("${services.billing.base-url}") String url) {

        this.client = builder.baseUrl(url).build();
    }

    public BillingResponse getFinalBill(Long bookingId) {

        try {

            JsonNode node = client.get()
                    .uri("/api/billing/my-bill/{bookingId}", bookingId)
                    .retrieve()
                    .body(JsonNode.class);

            return new BillingResponse(
                    bookingId,
                    longValue(node, "branchId"),
                    longValue(node, "customerId"),
                    amount(node),
                    node.path("paymentStatus").asText(null)
            );

        } catch (Exception e) {
            throw new BusinessRuleException(
                    "Billing service unavailable or final bill not found"
            );
        }
    }

    private Long longValue(JsonNode node, String key) {
        return node.path(key).isNumber()
                ? node.get(key).longValue()
                : null;
    }

    private BigDecimal amount(JsonNode node) {

        for (String key : new String[]{
                "finalAmount",
                "amount",
                "payableAmount"
        }) {

            if (node.path(key).isNumber()) {
                return node.get(key).decimalValue();
            }
        }

        return BigDecimal.ZERO;
    }
}