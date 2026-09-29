package com.agacostays.support.repository;

import com.agacostays.support.entity.CustomerSupportRequest;
import com.agacostays.support.enums.SupportStatus;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerSupportRequestRepository extends JpaRepository<CustomerSupportRequest, Long> {
    List<CustomerSupportRequest> findByCustomerIdOrderByCreatedAtDesc(Long customerId);
    List<CustomerSupportRequest> findByBranchIdOrderByCreatedAtDesc(Long branchId);
    Page<CustomerSupportRequest> findByBranchId(Long branchId, Pageable pageable);
    Page<CustomerSupportRequest> findByBranchIdAndStatus(Long branchId, SupportStatus status, Pageable pageable);
    List<CustomerSupportRequest> findByStatusIn(List<SupportStatus> statuses);
}
