CREATE TABLE IF NOT EXISTS reports (
    report_id BIGSERIAL PRIMARY KEY,
    report_type VARCHAR(50) NOT NULL,
    report_format VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL,
    branch_id BIGINT,
    file_url VARCHAR(500),
    requested_by BIGINT,
    created_at TIMESTAMPTZ NOT NULL,
    completed_at TIMESTAMPTZ,
    error_message VARCHAR(1000)
);

CREATE TABLE IF NOT EXISTS hotel_revenue_projection (
    projection_id BIGSERIAL PRIMARY KEY,
    branch_id BIGINT NOT NULL,
    metric_date DATE NOT NULL,
    room_revenue NUMERIC(16,2) NOT NULL DEFAULT 0,
    restaurant_revenue NUMERIC(16,2) NOT NULL DEFAULT 0,
    total_revenue NUMERIC(16,2) NOT NULL DEFAULT 0,
    booking_count BIGINT NOT NULL DEFAULT 0,
    occupied_room_count BIGINT NOT NULL DEFAULT 0,
    customer_count BIGINT NOT NULL DEFAULT 0,
    updated_at TIMESTAMPTZ NOT NULL,
    CONSTRAINT uk_hotel_revenue_branch_date UNIQUE(branch_id,metric_date)
);

CREATE TABLE IF NOT EXISTS restaurant_sales_projection (
    projection_id BIGSERIAL PRIMARY KEY,
    branch_id BIGINT NOT NULL,
    metric_date DATE NOT NULL,
    sales_amount NUMERIC(16,2) NOT NULL DEFAULT 0,
    order_count BIGINT NOT NULL DEFAULT 0,
    delivered_count BIGINT NOT NULL DEFAULT 0,
    chef_performance_count BIGINT NOT NULL DEFAULT 0,
    serving_performance_count BIGINT NOT NULL DEFAULT 0,
    updated_at TIMESTAMPTZ NOT NULL,
    CONSTRAINT uk_restaurant_sales_branch_date UNIQUE(branch_id,metric_date)
);

CREATE TABLE IF NOT EXISTS attendance_projection (
    projection_id BIGSERIAL PRIMARY KEY,
    branch_id BIGINT NOT NULL,
    metric_date DATE NOT NULL,
    present_count BIGINT NOT NULL DEFAULT 0,
    absent_count BIGINT NOT NULL DEFAULT 0,
    half_day_count BIGINT NOT NULL DEFAULT 0,
    leave_count BIGINT NOT NULL DEFAULT 0,
    total_staff BIGINT NOT NULL DEFAULT 0,
    updated_at TIMESTAMPTZ NOT NULL,
    CONSTRAINT uk_attendance_projection_branch_date UNIQUE(branch_id,metric_date)
);

CREATE TABLE IF NOT EXISTS payroll_projection (
    projection_id BIGSERIAL PRIMARY KEY,
    branch_id BIGINT NOT NULL,
    metric_date DATE NOT NULL,
    gross_salary NUMERIC(16,2) NOT NULL DEFAULT 0,
    net_salary NUMERIC(16,2) NOT NULL DEFAULT 0,
    staff_count BIGINT NOT NULL DEFAULT 0,
    paid_count BIGINT NOT NULL DEFAULT 0,
    updated_at TIMESTAMPTZ NOT NULL,
    CONSTRAINT uk_payroll_projection_branch_date UNIQUE(branch_id,metric_date)
);

CREATE TABLE IF NOT EXISTS feedback_projection (
    projection_id BIGSERIAL PRIMARY KEY,
    branch_id BIGINT NOT NULL,
    metric_date DATE NOT NULL,
    hotel_rating_sum NUMERIC(16,2) NOT NULL DEFAULT 0,
    restaurant_rating_sum NUMERIC(16,2) NOT NULL DEFAULT 0,
    feedback_count BIGINT NOT NULL DEFAULT 0,
    restaurant_feedback_count BIGINT NOT NULL DEFAULT 0,
    updated_at TIMESTAMPTZ NOT NULL,
    CONSTRAINT uk_feedback_projection_branch_date UNIQUE(branch_id,metric_date)
);
