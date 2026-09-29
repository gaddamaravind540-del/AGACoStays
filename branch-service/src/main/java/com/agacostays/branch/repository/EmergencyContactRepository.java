package com.agacostays.branch.repository;
import com.agacostays.branch.entity.EmergencyContact;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface EmergencyContactRepository extends JpaRepository<EmergencyContact,Long>{
 List<EmergencyContact> findByBranch_BranchIdOrderByPurposeAsc(Long branchId);
}
