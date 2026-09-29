package com.agacostays.user.service.impl;
import com.agacostays.user.dto.response.CustomerResponse;
import com.agacostays.user.exception.CustomerNotFoundException;
import com.agacostays.user.mapper.CustomerMapper;
import com.agacostays.user.repository.CustomerRepository;
import com.agacostays.user.service.CustomerService;
import org.springframework.stereotype.Service;
@Service
public class CustomerServiceImpl implements CustomerService {
 private final CustomerRepository repo; private final CustomerMapper mapper;
 public CustomerServiceImpl(CustomerRepository repo,CustomerMapper mapper){this.repo=repo;this.mapper=mapper;}
 public CustomerResponse getCustomerByUserId(Long userId){
  return repo.findByUser_UserId(userId).map(mapper::toResponse).orElseThrow(()->new CustomerNotFoundException("Customer not found"));
 }
}
