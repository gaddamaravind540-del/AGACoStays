package com.agacostays.analytics.client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class RestaurantServiceClient {
    private final RestClient client;
    public RestaurantServiceClient(@Value("${service.restaurant.url}") String baseUrl,RestClient.Builder b){client=b.baseUrl(baseUrl).build();}
    public String healthReference(Long id) {
        try {
            return client.get().uri("/api/restaurant/orders/" + id).retrieve().toString();
        } catch(Exception e) {
            return null;
        }
    }
}
