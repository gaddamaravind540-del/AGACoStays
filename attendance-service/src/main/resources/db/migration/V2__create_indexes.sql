CREATE INDEX IF NOT EXISTS idx_attendance_branch_date ON attendance(branch_id,attendance_date);
CREATE INDEX IF NOT EXISTS idx_attendance_staff_date ON attendance(staff_id,attendance_date);
CREATE INDEX IF NOT EXISTS idx_attendance_status_date ON attendance(status,attendance_date);
CREATE INDEX IF NOT EXISTS idx_corrections_attendance ON attendance_corrections(attendance_id);
CREATE INDEX IF NOT EXISTS idx_corrections_staff ON attendance_corrections(staff_id);
