CREATE TABLE customer_support_requests (
    request_id BIGSERIAL PRIMARY KEY,
    branch_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    booking_id BIGINT,
    request_type VARCHAR(40) NOT NULL,
    subject VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    priority VARCHAR(20) NOT NULL,
    status VARCHAR(30) NOT NULL,
    assigned_to BIGINT,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE feedback (
    feedback_id BIGSERIAL PRIMARY KEY,
    branch_id BIGINT NOT NULL,
    booking_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    hotel_rating INTEGER,
    hotel_comments TEXT,
    restaurant_rating INTEGER,
    restaurant_comments TEXT,
    feedback_type VARCHAR(30),
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE restaurant_feedback (
    feedback_id BIGSERIAL PRIMARY KEY,
    branch_id BIGINT NOT NULL,
    booking_id BIGINT NOT NULL,
    order_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    food_rating INTEGER NOT NULL,
    taste_rating INTEGER NOT NULL,
    delivery_rating INTEGER NOT NULL,
    comments TEXT,
    created_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE support_assignments (
    assignment_id BIGSERIAL PRIMARY KEY,
    request_id BIGINT NOT NULL,
    assigned_to BIGINT NOT NULL,
    assigned_by BIGINT NOT NULL,
    assignment_status VARCHAR(30) NOT NULL,
    assigned_at TIMESTAMPTZ NOT NULL,
    completed_at TIMESTAMPTZ
);
