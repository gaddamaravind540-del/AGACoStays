package com.agacostays.analytics.client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class PayrollServiceClient {
    private final RestClient client;
    public PayrollServiceClient(@Value("${service.payroll.url}") String baseUrl,RestClient.Builder b){client=b.baseUrl(baseUrl).build();}
    public String healthReference(Long id) {
        try {
            return client.get().uri("/api/manager/payroll/" + id).retrieve().toString();
        } catch(Exception e) {
            return null;
        }
    }
}
