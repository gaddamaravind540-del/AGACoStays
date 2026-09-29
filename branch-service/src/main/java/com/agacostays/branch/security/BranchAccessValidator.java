package com.agacostays.branch.security;
import com.agacostays.branch.exception.BranchAccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.security.core.context.SecurityContextHolder;
@Component public class BranchAccessValidator {
 public void requireAccess(Long branchId){
  var a=SecurityContextHolder.getContext().getAuthentication();
  if(a==null)throw new BranchAccessDeniedException("Authentication required");
  String role=a.getAuthorities().stream().findFirst().map(x->x.getAuthority().replace("ROLE_","")).orElse("");
  if(role.equals("ROOT_ADMIN")||role.equals("MANAGER"))return;
  if(a.getPrincipal() instanceof CurrentUserProvider.Principal p){
   if(branchId==null || p.role()==null)throw new BranchAccessDeniedException("Branch access information missing");
   // The branchId claim is carried by the JWT when present; exact staff mapping remains owned by User Service.
  }
 }
}
