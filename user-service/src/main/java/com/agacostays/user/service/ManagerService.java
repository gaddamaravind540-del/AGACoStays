package com.agacostays.user.service;
import com.agacostays.user.dto.request.*; import com.agacostays.user.dto.response.*; public interface ManagerService {
 StaffResponse createStaff(CreateStaffRequest r,Long actor);
 PageResponse<StaffResponse> listStaff(int page,int size);
 StaffResponse getStaff(Long id);
 StaffResponse updateStaff(Long id,UpdateStaffRequest r,Long actor);
 StaffResponse assignRole(Long id,AssignRoleRequest r,Long actor);
 UserStatusResponse updateStaffStatus(Long id,boolean active,Long actor);
 StaffBranchMappingResponse assignStaffToBranch(Long staffId,AssignStaffToBranchRequest r,Long actor);
 StaffBranchMappingResponse transferBranch(Long staffId,TransferBranchRequest r,Long actor);
 PageResponse<CustomerResponse> listCustomers(int page,int size);
 CustomerResponse updateCustomerStatus(Long customerId,UpdateCustomerStatusRequest r,Long actor);
}