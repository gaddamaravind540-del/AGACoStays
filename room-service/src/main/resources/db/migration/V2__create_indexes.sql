CREATE INDEX idx_rooms_branch ON rooms(branch_id);
CREATE INDEX idx_rooms_branch_status ON rooms(branch_id, status);
CREATE INDEX idx_rooms_branch_type ON rooms(branch_id, room_type);
CREATE INDEX idx_room_photos_room ON room_photos(branch_id, room_id);
CREATE INDEX idx_room_price_history_room ON room_price_history(branch_id, room_id, effective_from DESC);
