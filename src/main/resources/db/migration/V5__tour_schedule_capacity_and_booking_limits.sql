ALTER TABLE tours
    ADD COLUMN IF NOT EXISTS start_date DATE;

ALTER TABLE tours
    ADD COLUMN IF NOT EXISTS start_time TIME;

ALTER TABLE tours
    ADD COLUMN IF NOT EXISTS capacity INTEGER;

UPDATE tours
SET start_date = COALESCE(start_date, CURRENT_DATE + 7),
    start_time = COALESCE(start_time, TIME '09:00'),
    capacity = COALESCE(capacity, 20)
WHERE start_date IS NULL
   OR start_time IS NULL
   OR capacity IS NULL;

UPDATE tour_bookings
SET people_count = 1
WHERE people_count IS NULL OR people_count <> 1;

DELETE FROM tour_bookings older
USING tour_bookings newer
WHERE older.id < newer.id
  AND older.user_id = newer.user_id
  AND older.tour_id = newer.tour_id;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM pg_constraint
        WHERE conname = 'uk_tour_bookings_user_tour'
    ) THEN
        ALTER TABLE tour_bookings
            ADD CONSTRAINT uk_tour_bookings_user_tour UNIQUE (user_id, tour_id);
    END IF;
END $$;
