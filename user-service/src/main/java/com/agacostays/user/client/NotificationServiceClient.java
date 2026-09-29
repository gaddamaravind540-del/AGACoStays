package com.agacostays.user.client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import java.util.Map;
@Component
public class NotificationServiceClient {
 private final RestClient client;
 public NotificationServiceClient(RestClient.Builder b,@Value("${app.clients.notification-service-url:http://localhost:8090}") String baseUrl){client=b.baseUrl(baseUrl).build();}
 public void sendStaffAssignedNotification(String email,String branchId){
  try{client.post().uri("/api/notifications/staff-assignment").body(Map.of("email",email,"branchId",branchId)).retrieve().toBodilessEntity();}catch(Exception ignored){}
 }
}
