package com.agacostays.user.controller;
import com.agacostays.user.dto.request.*; import com.agacostays.user.dto.response.*;
import com.agacostays.user.security.CurrentUserProvider; import com.agacostays.user.service.ManagerService;
import jakarta.validation.Valid; import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
@PreAuthorize("hasAnyRole('MANAGER','ROOT_ADMIN')")
@RestController @RequestMapping("/api/manager")
public class ManagerController {
 private final ManagerService s; private final CurrentUserProvider c;
 public ManagerController(ManagerService s,CurrentUserProvider c){this.s=s;this.c=c;}
 @PostMapping("/staff") public ApiResponse<StaffResponse> createStaff(@Valid @RequestBody CreateStaffRequest r){return ApiResponse.success("Staff created",s.createStaff(r,c.userId()),"unknown");}
 @GetMapping("/staff") public ApiResponse<PageResponse<StaffResponse>> staff(@RequestParam(defaultValue="0")int page,@RequestParam(defaultValue="20")int size){return ApiResponse.success("Staff",s.listStaff(page,size),"unknown");}
 @GetMapping("/staff/{id}") public ApiResponse<StaffResponse> getStaff(@PathVariable Long id){return ApiResponse.success("Staff",s.getStaff(id),"unknown");}
 @PutMapping("/staff/{id}") public ApiResponse<StaffResponse> updateStaff(@PathVariable Long id,@Valid @RequestBody UpdateStaffRequest r){return ApiResponse.success("Staff updated",s.updateStaff(id,r,c.userId()),"unknown");}
 @PutMapping("/staff/{id}/assign-role") public ApiResponse<StaffResponse> role(@PathVariable Long id,@Valid @RequestBody AssignRoleRequest r){return ApiResponse.success("Staff role updated",s.assignRole(id,r,c.userId()),"unknown");}
 @PutMapping("/staff/{id}/status") public ApiResponse<UserStatusResponse> status(@PathVariable Long id,@RequestParam boolean active){return ApiResponse.success("Staff status updated",s.updateStaffStatus(id,active,c.userId()),"unknown");}
 @PostMapping("/hotel-branches/{branchId}/staff") public ApiResponse<StaffBranchMappingResponse> assign(@PathVariable Long branchId,@Valid @RequestBody AssignStaffToBranchRequest r){
   AssignStaffToBranchRequest req=new AssignStaffToBranchRequest(r.staffId(),branchId,r.roleName(),r.department(),r.shift(),r.assignedFrom());
   return ApiResponse.success("Staff assigned to branch",s.assignStaffToBranch(req.staffId(),req,c.userId()),"unknown");
 }
 @PutMapping("/staff/{staffId}/transfer-branch") public ApiResponse<StaffBranchMappingResponse> transfer(@PathVariable Long staffId,@Valid @RequestBody TransferBranchRequest r){return ApiResponse.success("Staff transferred",s.transferBranch(staffId,r,c.userId()),"unknown");}
 @GetMapping("/customers") public ApiResponse<PageResponse<CustomerResponse>> customers(@RequestParam(defaultValue="0")int page,@RequestParam(defaultValue="20")int size){return ApiResponse.success("Customers",s.listCustomers(page,size),"unknown");}
 @PutMapping("/customers/{customerId}/status") public ApiResponse<CustomerResponse> customerStatus(@PathVariable Long customerId,@Valid @RequestBody UpdateCustomerStatusRequest r){return ApiResponse.success("Customer status updated",s.updateCustomerStatus(customerId,r,c.userId()),"unknown");}
}
