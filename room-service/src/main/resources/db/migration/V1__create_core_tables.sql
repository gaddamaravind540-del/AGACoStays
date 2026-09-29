CREATE TABLE rooms (
    room_id BIGSERIAL PRIMARY KEY,
    branch_id BIGINT NOT NULL,
    room_number VARCHAR(30) NOT NULL,
    room_type VARCHAR(30) NOT NULL,
    base_price_per_day NUMERIC(12,2) NOT NULL,
    current_price_per_day NUMERIC(12,2) NOT NULL,
    description VARCHAR(2000),
    floor INTEGER,
    status VARCHAR(30) NOT NULL,
    created_by BIGINT,
    updated_by BIGINT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_room_branch_number UNIQUE (branch_id, room_number)
);

CREATE TABLE room_photos (
    photo_id BIGSERIAL PRIMARY KEY,
    branch_id BIGINT NOT NULL,
    room_id BIGINT NOT NULL,
    photo_url VARCHAR(1000) NOT NULL,
    caption VARCHAR(500),
    is_primary BOOLEAN NOT NULL DEFAULT FALSE,
    uploaded_by BIGINT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_room_photos_room FOREIGN KEY (room_id) REFERENCES rooms(room_id)
);

CREATE TABLE room_price_history (
    price_history_id BIGSERIAL PRIMARY KEY,
    room_id BIGINT NOT NULL,
    branch_id BIGINT NOT NULL,
    old_price NUMERIC(12,2) NOT NULL,
    new_price NUMERIC(12,2) NOT NULL,
    changed_by BIGINT,
    changed_by_role VARCHAR(50),
    change_reason VARCHAR(100),
    effective_from DATE NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_price_history_room FOREIGN KEY (room_id) REFERENCES rooms(room_id)
);
