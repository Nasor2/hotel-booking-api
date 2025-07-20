-- V1__initial_schema.sql

-- User entity
CREATE TABLE app_user (
                          id BIGSERIAL PRIMARY KEY,
                          first_name VARCHAR(255) NOT NULL,
                          last_name VARCHAR(255) NOT NULL,
                          address VARCHAR(255) NOT NULL,
                          email VARCHAR(255) NOT NULL UNIQUE,
                          phone_number VARCHAR(255) NOT NULL
);

-- Room Entity
CREATE TABLE room (
                      id BIGSERIAL PRIMARY KEY,
                      name VARCHAR(255) NOT NULL,
                      room_category VARCHAR(255) NOT NULL,
                      price_per_night DOUBLE PRECISION NOT NULL
);

-- Booking Entity
CREATE TABLE booking (
                         id BIGSERIAL PRIMARY KEY,
                         room_id BIGINT NOT NULL,
                         user_id BIGINT NOT NULL,
                         entry_date DATE NOT NULL,
                         exit_date DATE NOT NULL,
    -- FKs
                         CONSTRAINT fk_booking_room FOREIGN KEY (room_id) REFERENCES room(id),
                         CONSTRAINT fk_booking_user FOREIGN KEY (user_id) REFERENCES app_user(id)
);

-- Indexes to improve performance
CREATE INDEX idx_booking_room_id ON booking (room_id);
CREATE INDEX idx_booking_user_id ON booking (user_id);