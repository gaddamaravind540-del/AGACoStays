package com.agacostays.user.controller;
import com.agacostays.user.dto.request.*; import com.agacostays.user.dto.response.*; import com.agacostays.user.service.StaffBranchService;
import com.agacostays.user.security.CurrentUserProvider; import jakarta.validation.Valid; import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
@PreAuthorize("hasAnyRole('MANAGER','ROOT_ADMIN')")
@RestController @RequestMapping("/api/manager/staff-branch")
public class StaffBranchController {
 private final StaffBranchService s; private final CurrentUserProvider c;
 public StaffBranchController(StaffBranchService s,CurrentUserProvider c){this.s=s;this.c=c;}
 @PostMapping("/{staffId}/assign") public ApiResponse<StaffBranchMappingResponse> assign(@PathVariable Long staffId,@Valid @RequestBody AssignStaffToBranchRequest r){return ApiResponse.success("Staff assigned",s.assign(staffId,r,c.userId()),"unknown");}
 @PutMapping("/{staffId}/transfer") public ApiResponse<StaffBranchMappingResponse> transfer(@PathVariable Long staffId,@Valid @RequestBody TransferBranchRequest r){return ApiResponse.success("Staff transferred",s.transfer(staffId,r,c.userId()),"unknown");}
}
