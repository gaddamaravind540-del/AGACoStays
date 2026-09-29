CREATE TABLE restaurants (
    restaurant_id BIGSERIAL PRIMARY KEY,
    branch_id BIGINT NOT NULL UNIQUE,
    restaurant_name VARCHAR(255) NOT NULL,
    description TEXT,
    opening_time TIME,
    closing_time TIME,
    status VARCHAR(30) NOT NULL,
    created_by BIGINT,
    updated_by BIGINT,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE restaurant_menu (
    menu_item_id BIGSERIAL PRIMARY KEY,
    branch_id BIGINT NOT NULL,
    restaurant_id BIGINT NOT NULL,
    item_name VARCHAR(255) NOT NULL,
    category VARCHAR(40) NOT NULL,
    description TEXT,
    price NUMERIC(12,2) NOT NULL,
    availability BOOLEAN NOT NULL,
    food_type VARCHAR(20) NOT NULL,
    preparation_time_minutes INTEGER,
    status VARCHAR(30) NOT NULL,
    created_by BIGINT,
    updated_by BIGINT,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE restaurant_menu_photos (
    photo_id BIGSERIAL PRIMARY KEY,
    branch_id BIGINT NOT NULL,
    restaurant_id BIGINT NOT NULL,
    menu_item_id BIGINT NOT NULL,
    photo_url TEXT NOT NULL,
    caption TEXT,
    is_primary BOOLEAN NOT NULL,
    uploaded_by BIGINT,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE restaurant_orders (
    order_id BIGSERIAL PRIMARY KEY,
    branch_id BIGINT NOT NULL,
    restaurant_id BIGINT NOT NULL,
    booking_id BIGINT,
    room_id BIGINT,
    customer_id BIGINT NOT NULL,
    total_amount NUMERIC(12,2) NOT NULL,
    order_status VARCHAR(40) NOT NULL,
    delivery_type VARCHAR(30) NOT NULL,
    payment_mode VARCHAR(30) NOT NULL,
    created_by BIGINT,
    updated_by BIGINT,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE restaurant_order_items (
    order_item_id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL,
    menu_item_id BIGINT NOT NULL,
    item_name VARCHAR(255) NOT NULL,
    quantity INTEGER NOT NULL,
    unit_price NUMERIC(12,2) NOT NULL,
    line_total NUMERIC(12,2) NOT NULL
);

CREATE TABLE restaurant_feedback (
    feedback_id BIGSERIAL PRIMARY KEY,
    branch_id BIGINT NOT NULL,
    restaurant_id BIGINT NOT NULL,
    order_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    rating INTEGER NOT NULL,
    comments TEXT,
    created_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE restaurant_order_assignment (
    assignment_id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL,
    chef_id BIGINT,
    serving_staff_id BIGINT,
    assigned_by BIGINT,
    created_at TIMESTAMPTZ NOT NULL
);
