CREATE INDEX idx_booking_branch ON bookings(branch_id);
CREATE INDEX idx_booking_customer ON bookings(customer_id);
CREATE INDEX idx_booking_room ON bookings(room_id);
CREATE INDEX idx_booking_dates ON bookings(check_in_date, check_out_date);
CREATE INDEX idx_booking_history_booking ON booking_history(booking_id);
CREATE INDEX idx_idempotency_created_at ON idempotency_records(created_at);
