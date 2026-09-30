package com.agacostays.payment.client;

import com.agacostays.payment.dto.response.BookingResponse;
import com.agacostays.payment.exception.BusinessRuleException;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;

@Component
public class BookingServiceClient {

    private final RestClient client;

    public BookingServiceClient(
            RestClient.Builder builder,
            @Value("${services.booking.base-url}") String url) {

        this.client = builder.baseUrl(url).build();
    }

    public BookingResponse getBooking(Long id) {

        try {

            JsonNode node = client.get()
                    .uri("/api/bookings/{id}", id)
                    .retrieve()
                    .body(JsonNode.class);

            return new BookingResponse(
                    id,
                    longValue(node, "branchId"),
                    longValue(node, "customerId"),
                    amount(node),
                    text(node, "bookingStatus"),
                    text(node, "paymentStatus")
            );

        } catch (Exception e) {
            throw new BusinessRuleException(
                    "Booking service unavailable or booking not found"
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
                "totalAmount",
                "price",
                "bookingAmount",
                "finalAmount"
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