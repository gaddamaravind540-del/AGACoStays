package com.agacostays.payroll.client;
import com.agacostays.payroll.dto.response.UserResponse; import org.springframework.beans.factory.annotation.Value; import org.springframework.stereotype.Component; import org.springframework.web.client.RestClient; import com.fasterxml.jackson.databind.JsonNode;
@Component public class UserServiceClient{
 private final RestClient client; public UserServiceClient(@Value("${service.user.url}")String u,RestClient.Builder b){client=b.baseUrl(u).build();}
 public UserResponse getUser(Long id){try{return client.get().uri("/api/users/{id}",id).retrieve().body(UserResponse.class);}catch(Exception e){return null;}}
 public java.util.List<Long> getActiveStaffIds(Long branchId){
  try{JsonNode n=client.get().uri(ub->ub.path("/api/manager/staff").queryParam("branchId",branchId).queryParam("status","ACTIVE").queryParam("size",100).build()).retrieve().body(JsonNode.class);if(n==null)return java.util.List.of();JsonNode d=n.has("data")?n.get("data"):n;JsonNode arr=d.has("content")?d.get("content"):d;java.util.List<Long> out=new java.util.ArrayList<>();if(arr.isArray())for(JsonNode x:arr){JsonNode id=x.get("staffId");if(id==null)id=x.get("userId");if(id!=null)out.add(id.asLong());}return out;}catch(Exception e){return java.util.List.of();}}
}
