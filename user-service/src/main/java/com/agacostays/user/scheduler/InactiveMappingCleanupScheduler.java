package com.agacostays.user.scheduler;
import com.agacostays.user.entity.StaffBranchMapping;
import com.agacostays.user.enums.BranchAssignmentStatus;
import com.agacostays.user.repository.StaffBranchMappingRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
@Component
public class InactiveMappingCleanupScheduler {
 private final StaffBranchMappingRepository repo;
 public InactiveMappingCleanupScheduler(StaffBranchMappingRepository r){repo=r;}
 @Scheduled(cron="${app.mapping-cleanup.cron:0 0 2 * * *}")
 public void cleanup(){
  // The repository intentionally retains historical mappings. This scheduled hook
  // is the source-required extension point; active/inactive status is managed by transfer operations.
 }
}
