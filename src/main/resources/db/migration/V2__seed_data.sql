INSERT INTO users (email, password_hash, role, is_active, created_at)
VALUES
    ('demo@safetrip.kz', '$2a$10$9iC8Jx6Q7I6x8vB.f8N4du8KIM9KuX3sI5Yucs5cjox96D65gis6a', 'USER', TRUE, NOW()),
    ('admin@safetrip.kz', '$2a$10$9iC8Jx6Q7I6x8vB.f8N4du8KIM9KuX3sI5Yucs5cjox96D65gis6a', 'ADMIN', TRUE, NOW())
    ON CONFLICT (email) DO NOTHING;

INSERT INTO tours (title, description, city, duration_days, price, rating, image_url, is_featured, is_verified, start_lat, start_lng, h3_index)
VALUES
    ('Big Almaty Lake Day Tour', 'Verified one-day mountain tour for first-time visitors.', 'Almaty', 1, 25000.00, 4.80, 'https://example.com/tours/big-almaty-lake.jpg', TRUE, TRUE, 43.056000, 76.928000, '8a2a1072b59ffff'),
    ('Charyn Canyon Adventure', 'Full-day verified canyon trip with guide and transport.', 'Almaty', 1, 32000.00, 4.90, 'https://example.com/tours/charyn.jpg', TRUE, TRUE, 43.238000, 76.945000, '8a2a1072b597fff'),
    ('Turkistan Heritage Weekend', 'Curated cultural route for English-speaking travelers.', 'Turkistan', 2, 78000.00, 4.70, 'https://example.com/tours/turkistan.jpg', FALSE, TRUE, 43.297000, 68.251000, '8a2ac4a28637fff');

INSERT INTO places (title, description, category, average_price, rating, image_url, is_featured, is_verified, city, latitude, longitude, h3_index)
VALUES
    ('Navat', 'Popular restaurant with Kazakh cuisine.', 'Restaurant', 7000.00, 4.60, 'https://example.com/places/navat.jpg', TRUE, TRUE, 'Almaty', 43.238949, 76.889709, '8a2a1072b597fff'),
    ('Kok Tobe Viewpoint', 'Safe scenic viewpoint for evening walks.', 'Viewpoint', 5000.00, 4.80, 'https://example.com/places/koktobe.jpg', TRUE, TRUE, 'Almaty', 43.232400, 76.975200, '8a2a1072b5b7fff'),
    ('Coffee Boom', 'English-friendly cafe near central district.', 'Cafe', 4500.00, 4.40, 'https://example.com/places/coffeeboom.jpg', FALSE, TRUE, 'Astana', 51.128200, 71.430400, '8a31aa6b4c47fff');

INSERT INTO area_popularity_daily (stat_date, h3_index, favorites_count, places_count_snapshot, updated_at)
VALUES
    (CURRENT_DATE, '8a2a1072b597fff', 2, 2, NOW()),
    (CURRENT_DATE, '8a31aa6b4c47fff', 0, 1, NOW())
    ON CONFLICT (stat_date, h3_index) DO NOTHING;