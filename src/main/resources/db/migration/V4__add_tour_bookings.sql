CREATE TABLE IF NOT EXISTS tour_bookings (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    tour_id BIGINT NOT NULL REFERENCES tours(id) ON DELETE CASCADE,
    full_name VARCHAR(255) NOT NULL,
    phone_number VARCHAR(64) NOT NULL,
    contact_email VARCHAR(255) NOT NULL,
    people_count INTEGER NOT NULL DEFAULT 1,
    notes TEXT,
    status VARCHAR(30) NOT NULL,
    ticket_code VARCHAR(64) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_tour_bookings_user_id ON tour_bookings(user_id);
CREATE INDEX IF NOT EXISTS idx_tour_bookings_tour_id ON tour_bookings(tour_id);
CREATE INDEX IF NOT EXISTS idx_tour_bookings_ticket_code ON tour_bookings(ticket_code);
