package com.agacostays.support.repository;

import com.agacostays.support.entity.SupportAssignment;
import com.agacostays.support.enums.AssignmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface SupportAssignmentRepository extends JpaRepository<SupportAssignment, Long> {
    Optional<SupportAssignment> findByRequestId(Long requestId);
    List<SupportAssignment> findByAssignedToAndAssignmentStatus(Long assignedTo, AssignmentStatus status);
}
