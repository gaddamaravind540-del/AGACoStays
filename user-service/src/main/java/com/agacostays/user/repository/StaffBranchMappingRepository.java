package com.agacostays.user.repository;

import com.agacostays.user.entity.StaffBranchMapping;
import com.agacostays.user.enums.BranchAssignmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StaffBranchMappingRepository
        extends JpaRepository<StaffBranchMapping, Long> {

    List<StaffBranchMapping> findByStaff_StaffId(Long staffId);

    List<StaffBranchMapping> findByStaff_User_UserId(Long userId);

    Optional<StaffBranchMapping>
    findByStaff_User_UserIdAndBranchIdAndStatus(
            Long userId,
            Long branchId,
            BranchAssignmentStatus status);

    boolean existsByStaff_StaffIdAndBranchIdAndStatus(
            Long staffId,
            Long branchId,
            BranchAssignmentStatus status);
}