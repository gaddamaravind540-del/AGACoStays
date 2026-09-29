package com.agacostays.payment.security;

import com.agacostays.payment.exception.AccessDeniedException;
import org.springframework.stereotype.Component;

@Component public class BranchAccessValidator {
 private final CurrentUserProvider current;
 public BranchAccessValidator(CurrentUserProvider current){this.current=current;}
 public void validate(Long branchId){ if(branchId==null)return; if(current.hasRole("ROOT_ADMIN")||current.hasRole("SYSTEM"))return; if(current.hasRole("MANAGER")||current.hasRole("RECEPTIONIST")||current.hasRole("RESTAURANT_ADMIN")){Long b=current.branchId(); if(b==null||!branchId.equals(b))throw new AccessDeniedException("Branch access denied");} }
}
