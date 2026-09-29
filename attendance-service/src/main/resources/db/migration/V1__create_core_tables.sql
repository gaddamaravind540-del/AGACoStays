CREATE TABLE IF NOT EXISTS attendance (
    attendance_id BIGSERIAL PRIMARY KEY,
    branch_id BIGINT NOT NULL,
    staff_id BIGINT NOT NULL,
    attendance_date DATE NOT NULL,
    status VARCHAR(30) NOT NULL,
    source VARCHAR(30) NOT NULL,
    leave_type VARCHAR(30) NOT NULL,
    check_in_time TIME,
    check_out_time TIME,
    worked_hours NUMERIC(8,2),
    remarks VARCHAR(500),
    created_by BIGINT,
    updated_by BIGINT,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,
    CONSTRAINT uk_attendance_staff_date UNIQUE (staff_id, attendance_date)
);

CREATE TABLE IF NOT EXISTS attendance_corrections (
    correction_id BIGSERIAL PRIMARY KEY,
    attendance_id BIGINT NOT NULL REFERENCES attendance(attendance_id) ON DELETE CASCADE,
    staff_id BIGINT NOT NULL,
    attendance_date DATE NOT NULL,
    requested_status VARCHAR(30),
    requested_check_in TIME,
    requested_check_out TIME,
    reason VARCHAR(500) NOT NULL,
    status VARCHAR(30) NOT NULL,
    requested_by BIGINT NOT NULL,
    approved_by BIGINT,
    created_at TIMESTAMPTZ NOT NULL
);
