package com.agacostays.branch.client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
@Component public class UserServiceClient{
 private final RestClient client;
 public UserServiceClient(RestClient.Builder b,@Value("${app.clients.user-service-url:http://localhost:8082}")String url){client=b.baseUrl(url).build();}
 public boolean userExists(Long id){try{client.get().uri("/api/users/{id}",id).retrieve().toBodilessEntity();return true;}catch(Exception e){return false;}}
}
