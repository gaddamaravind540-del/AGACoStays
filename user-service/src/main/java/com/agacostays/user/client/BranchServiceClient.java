package com.agacostays.user.client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class BranchServiceClient {
 private final RestClient client;
 public BranchServiceClient(RestClient.Builder b,@Value("${app.clients.branch-service-url:http://localhost:8083}") String baseUrl){client=b.baseUrl(baseUrl).build();}
 public boolean branchExists(Long branchId){
  try{client.get().uri("/api/hotel-branches/{id}",branchId).retrieve().toBodilessEntity();return true;}
  catch(Exception e){return false;}
 }
}
