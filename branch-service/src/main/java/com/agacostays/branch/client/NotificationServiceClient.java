package com.agacostays.branch.client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import java.util.Map;
@Component public class NotificationServiceClient{
 private final RestClient client;
 public NotificationServiceClient(RestClient.Builder b,@Value("${app.clients.notification-service-url:http://localhost:8090}")String url){client=b.baseUrl(url).build();}
 public void sendBranchUpdate(Long branchId,String type){
  try{client.post().uri("/api/notifications/branch-update").body(Map.of("branchId",branchId,"type",type)).retrieve().toBodilessEntity();}catch(Exception ignored){}
 }
}
