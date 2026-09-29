package com.agacostays.user.service;
import com.agacostays.user.dto.response.CustomerResponse; public interface CustomerService { CustomerResponse getCustomerByUserId(Long userId); }