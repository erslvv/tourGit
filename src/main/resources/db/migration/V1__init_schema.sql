CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_users_email UNIQUE (email)
);
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);

CREATE TABLE IF NOT EXISTS tours (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    city VARCHAR(100) NOT NULL,
    duration_days INTEGER NOT NULL,
    price NUMERIC(12,2) NOT NULL,
    rating NUMERIC(3,2),
    image_url TEXT,
    is_featured BOOLEAN DEFAULT FALSE,
    is_verified BOOLEAN DEFAULT FALSE,
    start_lat NUMERIC(9,6),
    start_lng NUMERIC(9,6),
    h3_index VARCHAR(32),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX IF NOT EXISTS idx_tours_city ON tours(city);
CREATE INDEX IF NOT EXISTS idx_tours_h3_index ON tours(h3_index);
CREATE INDEX IF NOT EXISTS idx_tours_featured ON tours(is_featured);
CREATE INDEX IF NOT EXISTS idx_tours_verified ON tours(is_verified);

CREATE TABLE IF NOT EXISTS places (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    category VARCHAR(50) NOT NULL,
    average_price NUMERIC(12,2),
    rating NUMERIC(3,2),
    image_url TEXT,
    is_featured BOOLEAN DEFAULT FALSE,
    is_verified BOOLEAN DEFAULT FALSE,
    city VARCHAR(100) NOT NULL,
    latitude NUMERIC(9,6) NOT NULL,
    longitude NUMERIC(9,6) NOT NULL,
    h3_index VARCHAR(32) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX IF NOT EXISTS idx_places_city ON places(city);
CREATE INDEX IF NOT EXISTS idx_places_category ON places(category);
CREATE INDEX IF NOT EXISTS idx_places_h3_index ON places(h3_index);
CREATE INDEX IF NOT EXISTS idx_places_featured ON places(is_featured);
CREATE INDEX IF NOT EXISTS idx_places_verified ON places(is_verified);

CREATE TABLE IF NOT EXISTS favorite_tours (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    tour_id BIGINT NOT NULL REFERENCES tours(id) ON DELETE CASCADE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_favorite_tours_user_tour UNIQUE (user_id, tour_id)
);
CREATE INDEX IF NOT EXISTS idx_favorite_tours_user_id ON favorite_tours(user_id);
CREATE INDEX IF NOT EXISTS idx_favorite_tours_tour_id ON favorite_tours(tour_id);

CREATE TABLE IF NOT EXISTS favorite_places (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    place_id BIGINT NOT NULL REFERENCES places(id) ON DELETE CASCADE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_favorite_places_user_place UNIQUE (user_id, place_id)
);
CREATE INDEX IF NOT EXISTS idx_favorite_places_user_id ON favorite_places(user_id);
CREATE INDEX IF NOT EXISTS idx_favorite_places_place_id ON favorite_places(place_id);

CREATE TABLE IF NOT EXISTS audit_log (
    id BIGSERIAL PRIMARY KEY,
    event_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_id BIGINT REFERENCES users(id),
    action VARCHAR(100) NOT NULL,
    entity_type VARCHAR(50),
    entity_id BIGINT,
    status VARCHAR(20) NOT NULL,
    ip_address VARCHAR(64),
    details_json JSONB
);
CREATE INDEX IF NOT EXISTS idx_audit_log_event_time ON audit_log(event_time);
CREATE INDEX IF NOT EXISTS idx_audit_log_user_id ON audit_log(user_id);
CREATE INDEX IF NOT EXISTS idx_audit_log_entity ON audit_log(entity_type, entity_id);
CREATE INDEX IF NOT EXISTS idx_audit_log_action ON audit_log(action);
CREATE INDEX IF NOT EXISTS idx_audit_log_status ON audit_log(status);

CREATE TABLE IF NOT EXISTS area_popularity_daily (
    id BIGSERIAL PRIMARY KEY,
    stat_date DATE NOT NULL,
    h3_index VARCHAR(32) NOT NULL,
    favorites_count INTEGER NOT NULL DEFAULT 0,
    places_count_snapshot INTEGER,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_area_popularity_daily_date_h3 UNIQUE (stat_date, h3_index)
);
CREATE INDEX IF NOT EXISTS idx_area_popularity_daily_stat_date ON area_popularity_daily(stat_date);
CREATE INDEX IF NOT EXISTS idx_area_popularity_daily_h3_index ON area_popularity_daily(h3_index);
