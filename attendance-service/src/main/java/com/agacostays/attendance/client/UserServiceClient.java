package com.agacostays.attendance.client;
import com.agacostays.attendance.dto.response.UserResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
@Component
public class UserServiceClient {
    private final RestClient client;
    public UserServiceClient(@Value("${service.user.url}")String baseUrl,RestClient.Builder b){client=b.baseUrl(baseUrl).build();}
    public UserResponse getUser(Long userId){
        try{return client.get().uri("/api/users/{id}",userId).retrieve().body(UserResponse.class);}
        catch(Exception e){return null;}
    }
    public boolean validateUser(Long userId){ return userId != null && (getUser(userId) != null || userId > 0); }
}
