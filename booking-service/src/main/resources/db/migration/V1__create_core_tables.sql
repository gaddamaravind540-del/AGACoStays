CREATE TABLE bookings (
    booking_id BIGSERIAL PRIMARY KEY,
    branch_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    room_id BIGINT NOT NULL,
    check_in_date DATE NOT NULL,
    check_out_date DATE NOT NULL,
    check_in_time TIME NOT NULL,
    check_out_time TIME NOT NULL,
    number_of_guests INTEGER NOT NULL,
    number_of_days INTEGER NOT NULL,
    price_per_day_at_booking NUMERIC(12,2) NOT NULL,
    room_charges NUMERIC(12,2) NOT NULL,
    booking_status VARCHAR(30) NOT NULL,
    payment_status VARCHAR(30) NOT NULL,
    booking_source VARCHAR(30) NOT NULL,
    created_by BIGINT,
    updated_by BIGINT,
    guest_name VARCHAR(255),
    guest_id_proof_type VARCHAR(30),
    guest_id_proof_number VARCHAR(255),
    check_in_completed_at TIMESTAMPTZ,
    check_out_completed_at TIMESTAMPTZ,
    cancellation_reason VARCHAR(60),
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE booking_history (
    history_id BIGSERIAL PRIMARY KEY,
    booking_id BIGINT NOT NULL,
    action VARCHAR(40) NOT NULL,
    booking_status VARCHAR(30) NOT NULL,
    changed_by BIGINT,
    changed_by_role VARCHAR(50),
    remarks TEXT,
    created_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE idempotency_records (
    id BIGSERIAL PRIMARY KEY,
    idempotency_key VARCHAR(120) NOT NULL UNIQUE,
    request_hash VARCHAR(128) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL
);
