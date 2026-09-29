package com.agacostays.branch.repository;
import com.agacostays.branch.entity.HotelBranch;
import com.agacostays.branch.enums.BranchStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface HotelBranchRepository extends JpaRepository<HotelBranch,Long>{
 List<HotelBranch> findByStatusOrderByBranchNameAsc(BranchStatus status);
 List<HotelBranch> findByCity_CityIdAndStatusOrderByBranchNameAsc(Long cityId,BranchStatus status);
 Optional<HotelBranch> findByBranchNameIgnoreCase(String branchName);
}
