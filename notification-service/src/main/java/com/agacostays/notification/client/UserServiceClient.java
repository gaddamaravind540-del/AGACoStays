package com.agacostays.notification.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Component
public class UserServiceClient {
    private final RestClient client;
    private final String baseUrl;
    public UserServiceClient(RestClient client,@Value("${notification.user-service.base-url:http://localhost:8082}") String baseUrl){this.client=client;this.baseUrl=baseUrl;}
    public String customerEmail(Long customerId){
        if(customerId==null) return null;
        try{
            var response=client.get().uri(baseUrl+"/api/internal/customers/"+customerId).retrieve().body(Map.class);
            if(response==null) return null;
            Object email=response.get("email");
            return email==null?null:String.valueOf(email);
        }catch(Exception ignored){return null;}
    }
}
