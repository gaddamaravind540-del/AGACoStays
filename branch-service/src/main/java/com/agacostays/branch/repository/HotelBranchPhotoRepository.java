package com.agacostays.branch.repository;
import com.agacostays.branch.entity.HotelBranchPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface HotelBranchPhotoRepository extends JpaRepository<HotelBranchPhoto,Long>{
 List<HotelBranchPhoto> findByBranch_BranchIdOrderByPrimaryPhotoDescCreatedAtAsc(Long branchId);
}
