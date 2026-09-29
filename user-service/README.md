# AGA CoStays User Service

Application: `user-service`  
Base package: `com.agacostays.user`  
Database: `user_db`  
Port: `8082`

This service owns the user-facing operational data required by the AGA CoStays architecture: Root Admin/Manager/Staff/Customer profiles, roles, permissions and staff-branch mappings.

## Main APIs from the source

- POST /api/root-admin/managers
- GET /api/root-admin/managers
- GET /api/root-admin/managers/{managerId}
- PUT /api/root-admin/managers/{managerId}
- PUT /api/root-admin/managers/{managerId}/status
- DELETE /api/root-admin/managers/{managerId}
- POST /api/manager/staff
- GET /api/manager/staff
- GET /api/manager/staff/{staffId}
- PUT /api/manager/staff/{staffId}
- PUT /api/manager/staff/{staffId}/assign-role
- PUT /api/manager/staff/{staffId}/status
- POST /api/manager/hotel-branches/{branchId}/staff
- PUT /api/manager/staff/{staffId}/transfer-branch
- GET /api/manager/customers
- PUT /api/manager/customers/{customerId}/status
- POST /api/roles
- GET /api/roles
- POST /api/permissions
- POST /api/permissions/roles/{roleId}
- GET /api/profile/me
- PUT /api/profile/me

## Source vs implementation details

The source explicitly defines the table names/fields and service package structure. Java data types, token-validation implementation, password hashing, JWT parsing, and some enum values are implementation details in this project.


## Integration note
The source endpoint `POST /api/manager/hotel-branches/{branchId}/staff` does not show a staffId field in its path. This implementation therefore includes `staffId` in `AssignStaffToBranchRequest` so the request identifies the staff member unambiguously. This is an implementation choice made to close that source-level ambiguity.
