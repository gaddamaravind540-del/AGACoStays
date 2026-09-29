package com.agacostays.branch.security;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
@Component public class CurrentUserProvider {
 public Long userId(){
  Authentication a=SecurityContextHolder.getContext().getAuthentication();
  if(a==null||!(a.getPrincipal() instanceof Principal p)) throw new IllegalStateException("Authenticated identity missing");
  return p.userId();
 }
 public String role(){
  Authentication a=SecurityContextHolder.getContext().getAuthentication();
  if(a==null)return null;
  return a.getAuthorities().stream().findFirst().map(x->x.getAuthority().replace("ROLE_","")).orElse(null);
 }
 public record Principal(Long userId,String email,String role){}
}
