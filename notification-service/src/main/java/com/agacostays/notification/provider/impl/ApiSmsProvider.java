package com.agacostays.notification.provider.impl;

import com.agacostays.notification.provider.ProviderResult;
import com.agacostays.notification.provider.SmsProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import java.util.Map;

@Component
public class ApiSmsProvider implements SmsProvider {
    private final RestClient client; private final boolean enabled; private final String apiUrl; private final String apiKey; private final String senderId;
    public ApiSmsProvider(RestClient client,
                          @Value("${notification.sms.enabled:false}") boolean enabled,
                          @Value("${notification.sms.api-url:}") String apiUrl,
                          @Value("${notification.sms.api-key:}") String apiKey,
                          @Value("${notification.sms.sender-id:AGACOSTAYS}") String senderId){
        this.client=client; this.enabled=enabled; this.apiUrl=apiUrl; this.apiKey=apiKey; this.senderId=senderId;
    }
    @Override public ProviderResult send(String phone, String message){
        if(!enabled) return new ProviderResult(false,"SMS API provider is disabled");
        if(apiUrl==null || apiUrl.isBlank()) return new ProviderResult(false,"SMS API URL is not configured");
        try{
            client.post().uri(apiUrl).contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + apiKey)
                .body(Map.of("to",phone,"message",message,"senderId",senderId))
                .retrieve().toBodilessEntity();
            return new ProviderResult(true,"SMS sent through configured API");
        }catch(Exception ex){ return new ProviderResult(false, ex.getMessage()); }
    }
}
