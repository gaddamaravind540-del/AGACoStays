CREATE TABLE payments (
    payment_id BIGSERIAL PRIMARY KEY,
    branch_id BIGINT NOT NULL,
    payment_for VARCHAR(40) NOT NULL,
    reference_id VARCHAR(100) NOT NULL,
    customer_id BIGINT NOT NULL,
    amount NUMERIC(14,2) NOT NULL,
    currency VARCHAR(8) NOT NULL,
    payment_method VARCHAR(40) NOT NULL,
    payment_status VARCHAR(40) NOT NULL,
    gateway_name VARCHAR(40) NOT NULL,
    gateway_order_id VARCHAR(120),
    gateway_payment_id VARCHAR(120),
    gateway_signature VARCHAR(512),
    paid_at TIMESTAMPTZ,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE payment_transactions (
    transaction_id BIGSERIAL PRIMARY KEY,
    payment_id BIGINT NOT NULL,
    transaction_type VARCHAR(40) NOT NULL,
    amount NUMERIC(14,2) NOT NULL,
    gateway_transaction_id VARCHAR(120),
    status VARCHAR(40) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE refunds (
    refund_id BIGSERIAL PRIMARY KEY,
    payment_id BIGINT NOT NULL,
    amount NUMERIC(14,2) NOT NULL,
    refund_status VARCHAR(40) NOT NULL,
    refund_reason VARCHAR(60) NOT NULL,
    gateway_refund_id VARCHAR(120),
    created_at TIMESTAMPTZ NOT NULL,
    processed_at TIMESTAMPTZ
);

CREATE TABLE payment_webhook_logs (
    webhook_log_id BIGSERIAL PRIMARY KEY,
    event_type VARCHAR(60) NOT NULL,
    gateway_order_id VARCHAR(120),
    gateway_payment_id VARCHAR(120),
    signature VARCHAR(512),
    raw_payload TEXT NOT NULL,
    verified BOOLEAN NOT NULL,
    received_at TIMESTAMPTZ NOT NULL,
    processed_at TIMESTAMPTZ
);

CREATE TABLE payment_idempotency_records (
    id BIGSERIAL PRIMARY KEY,
    idempotency_key VARCHAR(160) NOT NULL UNIQUE,
    request_hash VARCHAR(64) NOT NULL,
    payment_id BIGINT,
    created_at TIMESTAMPTZ NOT NULL,
    expires_at TIMESTAMPTZ NOT NULL
);
