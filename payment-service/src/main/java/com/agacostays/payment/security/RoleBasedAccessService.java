package com.agacostays.payment.security;
import org.springframework.stereotype.Component;
@Component public class RoleBasedAccessService { private final CurrentUserProvider current; public RoleBasedAccessService(CurrentUserProvider c){this.current=c;} public boolean isCustomer(){return current.hasRole("CUSTOMER");} public boolean isStaff(){return current.hasRole("MANAGER")||current.hasRole("RECEPTIONIST")||current.hasRole("ROOT_ADMIN");} public boolean isSystem(){return current.hasRole("SYSTEM");} }
