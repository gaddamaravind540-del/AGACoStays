INSERT INTO roles (role_name, description) VALUES
('ROOT_ADMIN', 'Platform owner with global access'),
('MANAGER', 'Business operations administrator'),
('CUSTOMER', 'Hotel customer'),
('RECEPTIONIST', 'Assigned-branch front desk role'),
('RESTAURANT_ADMIN', 'Assigned-branch restaurant administrator'),
('CHEF', 'Assigned-branch kitchen staff'),
('SERVING_STAFF', 'Assigned-branch food service staff'),
('HOUSEKEEPING_STAFF', 'Assigned-branch housekeeping staff')
ON CONFLICT (role_name) DO NOTHING;
