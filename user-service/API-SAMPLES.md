# AGA CoStays User Service API Samples

Base URL: http://localhost:8082

## Create Manager
POST /api/root-admin/managers
Authorization: Bearer <ROOT_ADMIN_TOKEN>
Content-Type: application/json

{
  "fullName": "Hotel Manager",
  "email": "manager@agacostays.com",
  "phone": "9876543210",
  "password": "Manager@123",
  "accessLevel": "ALL_BRANCHES"
}

## Create Staff
POST /api/manager/staff
Authorization: Bearer <MANAGER_TOKEN>

{
  "fullName": "Reception Staff",
  "email": "staff@agacostays.com",
  "phone": "9876500000",
  "password": "Staff@123",
  "department": "HOTEL_FRONT_DESK",
  "shift": "MORNING",
  "joiningDate": "2026-09-29",
  "roleName": "RECEPTIONIST"
}

## Assign Staff to Branch
POST /api/manager/hotel-branches/1/staff
Authorization: Bearer <MANAGER_TOKEN>

{
  "staffId": 1,
  "roleName": "RECEPTIONIST",
  "department": "HOTEL_FRONT_DESK",
  "shift": "MORNING",
  "assignedFrom": "2026-09-29"
}

## List Customers
GET /api/manager/customers?page=0&size=20
Authorization: Bearer <MANAGER_TOKEN>

## My Profile
GET /api/profile/me
Authorization: Bearer <ACCESS_TOKEN>

## Update Profile
PUT /api/profile/me
Authorization: Bearer <ACCESS_TOKEN>

{
  "fullName": "Updated Name",
  "phone": "9876543210",
  "address": "Bengaluru"
}

## Create Role
POST /api/roles
Authorization: Bearer <ROOT_ADMIN_TOKEN>

{
  "roleName": "CUSTOM_SUPPORT",
  "description": "Custom support role"
}

## Create Permission
POST /api/permissions
Authorization: Bearer <ROOT_ADMIN_TOKEN>

{
  "permissionCode": "SUPPORT_READ",
  "permissionName": "Read Support",
  "description": "Read support requests",
  "moduleName": "SUPPORT"
}
