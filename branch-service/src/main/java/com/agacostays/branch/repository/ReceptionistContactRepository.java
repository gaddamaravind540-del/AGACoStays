package com.agacostays.branch.repository;
import com.agacostays.branch.entity.ReceptionistContact;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface ReceptionistContactRepository extends JpaRepository<ReceptionistContact,Long>{
 List<ReceptionistContact> findByBranch_BranchIdOrderByShiftAsc(Long branchId);
}
