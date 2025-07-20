-- V1__initial_schema.sql

-- Tabla para la entidad User
CREATE TABLE app_user (
                          id BIGSERIAL PRIMARY KEY,
                          first_name VARCHAR(255) NOT NULL,
                          last_name VARCHAR(255) NOT NULL,
                          address VARCHAR(255) NOT NULL,
                          email VARCHAR(255) NOT NULL UNIQUE, -- Asegúrate de que el email sea único
                          phone_number VARCHAR(255) NOT NULL
);

-- Tabla para la entidad Room
CREATE TABLE room (
                      id BIGSERIAL PRIMARY KEY,
                      name VARCHAR(255) NOT NULL,
                      room_category VARCHAR(255) NOT NULL, -- Para el ENUM RoomType
                      price_per_night DOUBLE PRECISION NOT NULL
);

-- Tabla para la entidad Booking
CREATE TABLE booking (
                         id BIGSERIAL PRIMARY KEY,
                         room_id BIGINT NOT NULL,
                         user_id BIGINT NOT NULL,
                         entry_date DATE NOT NULL,
                         exit_date DATE NOT NULL,
    -- Claves foráneas
                         CONSTRAINT fk_booking_room FOREIGN KEY (room_id) REFERENCES room(id),
                         CONSTRAINT fk_booking_user FOREIGN KEY (user_id) REFERENCES app_user(id)
);

-- Opcional: Añadir índices para mejorar el rendimiento de las búsquedas en claves foráneas
CREATE INDEX idx_booking_room_id ON booking (room_id);
CREATE INDEX idx_booking_user_id ON booking (user_id);