package com.agacostays.user.controller;
import com.agacostays.user.dto.response.*; import com.agacostays.user.security.CurrentUserProvider; import com.agacostays.user.service.CustomerService;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
@RestController @RequestMapping("/api/customers")
public class CustomerController {
 private final CustomerService s; private final CurrentUserProvider c;
 public CustomerController(CustomerService s,CurrentUserProvider c){this.s=s;this.c=c;}
 @PreAuthorize("hasRole('CUSTOMER')")
 @GetMapping("/me") public ApiResponse<CustomerResponse> me(){return ApiResponse.success("Customer profile",s.getCustomerByUserId(c.userId()),"unknown");}
}
