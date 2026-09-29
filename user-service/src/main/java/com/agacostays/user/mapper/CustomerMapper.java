package com.agacostays.user.mapper;
import com.agacostays.user.dto.response.CustomerResponse;
import com.agacostays.user.entity.Customer;
import org.springframework.stereotype.Component;
@Component
public class CustomerMapper {
    public CustomerResponse toResponse(Customer c){
        return new CustomerResponse(c.getCustomerId(),c.getUser().getUserId(),c.getUser().getFullName(),c.getUser().getEmail(),c.getUser().getPhone(),c.getAddress(),c.getStatus().name());
    }
}
