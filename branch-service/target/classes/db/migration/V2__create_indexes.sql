CREATE INDEX idx_cities_name ON cities(city_name);
CREATE INDEX idx_branch_city ON hotel_branches(city_id);
CREATE INDEX idx_branch_status ON hotel_branches(status);
CREATE INDEX idx_branch_photo_branch ON hotel_branch_photos(branch_id);
CREATE INDEX idx_reception_branch ON receptionist_contacts(branch_id);
CREATE INDEX idx_emergency_branch ON emergency_contacts(branch_id);
