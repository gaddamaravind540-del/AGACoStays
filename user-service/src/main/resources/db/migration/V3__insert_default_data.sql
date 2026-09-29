INSERT INTO roles(role_name,description,status) VALUES
('ROOT_ADMIN','Platform owner','ACTIVE'),
('MANAGER','Business operations administrator','ACTIVE'),
('CUSTOMER','Hotel customer','ACTIVE'),
('RECEPTIONIST','Assigned branch front desk role','ACTIVE'),
('RESTAURANT_ADMIN','Assigned branch restaurant administrator','ACTIVE'),
('CHEF','Assigned branch kitchen staff','ACTIVE'),
('SERVING_STAFF','Assigned branch service staff','ACTIVE'),
('HOUSEKEEPING_STAFF','Assigned branch housekeeping staff','ACTIVE')
ON CONFLICT(role_name) DO NOTHING;

INSERT INTO permissions(permission_code,permission_name,description,module_name) VALUES
('USER_CREATE','Create User','Create user accounts','USERS'),
('USER_READ','Read User','Read user accounts','USERS'),
('USER_UPDATE','Update User','Update user accounts','USERS'),
('USER_DELETE','Delete User','Deactivate user accounts','USERS'),
('ROLE_MANAGE','Manage Roles','Create and update roles','ROLES'),
('PERMISSION_MANAGE','Manage Permissions','Create and assign permissions','PERMISSIONS'),
('STAFF_MANAGE','Manage Staff','Create/update branch staff','STAFF'),
('CUSTOMER_MANAGE','Manage Customers','Update customer status','CUSTOMERS'),
('BRANCH_ASSIGN','Assign Staff Branch','Assign or transfer staff','BRANCHES')
ON CONFLICT(permission_code) DO NOTHING;
