CREATE TABLE cities (
 city_id BIGSERIAL PRIMARY KEY,
 city_name VARCHAR(100) NOT NULL UNIQUE,
 state VARCHAR(100) NOT NULL,
 country VARCHAR(100) NOT NULL,
 status VARCHAR(20) NOT NULL,
 created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
 updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE hotel_branches (
 branch_id BIGSERIAL PRIMARY KEY,
 city_id BIGINT NOT NULL,
 branch_name VARCHAR(150) NOT NULL UNIQUE,
 address VARCHAR(500) NOT NULL,
 landmark VARCHAR(255),
 latitude NUMERIC(10,7),
 longitude NUMERIC(10,7),
 phone VARCHAR(20),
 email VARCHAR(180),
 description VARCHAR(1000),
 status VARCHAR(20) NOT NULL,
 created_by BIGINT,
 created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
 updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
 CONSTRAINT fk_branch_city FOREIGN KEY(city_id) REFERENCES cities(city_id)
);

CREATE TABLE hotel_branch_photos (
 photo_id BIGSERIAL PRIMARY KEY,
 branch_id BIGINT NOT NULL,
 photo_url VARCHAR(1000) NOT NULL,
 caption VARCHAR(255),
 photo_type VARCHAR(30) NOT NULL,
 is_primary BOOLEAN NOT NULL DEFAULT FALSE,
 uploaded_by BIGINT,
 created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
 updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
 CONSTRAINT fk_branch_photo_branch FOREIGN KEY(branch_id) REFERENCES hotel_branches(branch_id)
);

CREATE TABLE receptionist_contacts (
 contact_id BIGSERIAL PRIMARY KEY,
 branch_id BIGINT NOT NULL,
 staff_id BIGINT,
 phone VARCHAR(20) NOT NULL,
 alternate_phone VARCHAR(20),
 email VARCHAR(180),
 shift VARCHAR(20),
 available_from TIME,
 available_to TIME,
 purpose VARCHAR(100),
 is_emergency_contact BOOLEAN NOT NULL DEFAULT FALSE,
 status VARCHAR(20) NOT NULL,
 created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
 updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
 CONSTRAINT fk_reception_branch FOREIGN KEY(branch_id) REFERENCES hotel_branches(branch_id)
);

CREATE TABLE emergency_contacts (
 contact_id BIGSERIAL PRIMARY KEY,
 branch_id BIGINT NOT NULL,
 contact_name VARCHAR(150) NOT NULL,
 phone VARCHAR(20) NOT NULL,
 alternate_phone VARCHAR(20),
 email VARCHAR(180),
 purpose VARCHAR(30) NOT NULL,
 status VARCHAR(20) NOT NULL,
 created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
 updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
 CONSTRAINT fk_emergency_branch FOREIGN KEY(branch_id) REFERENCES hotel_branches(branch_id)
);
