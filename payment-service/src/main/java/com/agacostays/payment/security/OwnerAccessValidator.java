package com.agacostays.payment.security;
import com.agacostays.payment.entity.Payment;
import com.agacostays.payment.exception.AccessDeniedException;
import org.springframework.stereotype.Component;
@Component public class OwnerAccessValidator { private final CurrentUserProvider current; public OwnerAccessValidator(CurrentUserProvider c){this.current=c;} public void validate(Payment p){ if(current.hasRole("ROOT_ADMIN")||current.hasRole("MANAGER")||current.hasRole("RECEPTIONIST")||current.hasRole("SYSTEM"))return; if(p.getPaymentFor()==com.agacostays.payment.enums.PaymentFor.RESTAURANT_ORDER && current.hasRole("RESTAURANT_ADMIN"))return; if(!current.hasRole("CUSTOMER")||p.getCustomerId()!=current.customerId())throw new AccessDeniedException("Payment ownership denied"); }}
