package com.agacostays.payment.client;

import com.agacostays.payment.dto.response.RestaurantOrderResponse;
import com.agacostays.payment.exception.BusinessRuleException;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;

@Component
public class RestaurantServiceClient {

    private final RestClient client;

    public RestaurantServiceClient(
            RestClient.Builder builder,
            @Value("${services.restaurant.base-url}") String url) {

        this.client = builder.baseUrl(url).build();
    }

    public RestaurantOrderResponse getOrder(String id) {

        try {

            JsonNode node = client.get()
                    .uri("/api/restaurant/orders/{id}", id)
                    .retrieve()
                    .body(JsonNode.class);

            return new RestaurantOrderResponse(
                    id,
                    longValue(node, "branchId"),
                    longValue(node, "customerId"),
                    amount(node),
                    text(node, "paymentStatus"),
                    text(node, "orderStatus")
            );

        } catch (Exception e) {
            throw new BusinessRuleException(
                    "Restaurant service unavailable or order not found"
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
                "amount",
                "orderAmount",
                "totalAmount"
        }) {

            if (node.path(key).isNumber()) {
                return node.get(key).decimalValue();
            }
        }

        return BigDecimal.ZERO;
    }

    private String text(JsonNode node, String key) {
        return node.path(key).isMissingNode()
                ? null
                : node.path(key).asText();
    }
}