ALTER TABLE users
    ADD COLUMN IF NOT EXISTS telegram_chat_id VARCHAR(64),
    ADD COLUMN IF NOT EXISTS telegram_username VARCHAR(255),
    ADD COLUMN IF NOT EXISTS telegram_verified BOOLEAN NOT NULL DEFAULT FALSE;

CREATE INDEX IF NOT EXISTS idx_users_telegram_chat_id
    ON users(telegram_chat_id);

CREATE UNIQUE INDEX IF NOT EXISTS idx_users_telegram_chat_id_unique
    ON users(telegram_chat_id)
    WHERE telegram_chat_id IS NOT NULL;

CREATE TABLE IF NOT EXISTS telegram_bind_tokens (
                                                    id BIGSERIAL PRIMARY KEY,
                                                    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    code_hash VARCHAR(64) NOT NULL UNIQUE,
    expires_at TIMESTAMP NOT NULL,
    used_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    );

CREATE INDEX IF NOT EXISTS idx_telegram_bind_tokens_code_hash
    ON telegram_bind_tokens(code_hash);

CREATE INDEX IF NOT EXISTS idx_telegram_bind_tokens_user_id
    ON telegram_bind_tokens(user_id);

CREATE TABLE IF NOT EXISTS password_reset_otps (
                                                   id BIGSERIAL PRIMARY KEY,
                                                   user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    otp_hash VARCHAR(64) NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    used_at TIMESTAMP,
    attempts INTEGER NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    );

CREATE INDEX IF NOT EXISTS idx_password_reset_otps_user_id
    ON password_reset_otps(user_id);

CREATE INDEX IF NOT EXISTS idx_password_reset_otps_created_at
    ON password_reset_otps(created_at);
