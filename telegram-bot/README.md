# SafeTrip Telegram Bot

This bot connects a Telegram chat to a SafeTrip user and sends password reset OTP codes.

## Environment variables

```env
BOT_TOKEN=123456:telegram_bot_token
BOT_SHARED_SECRET=change-me-long-random-secret
JAVA_API_BASE_URL=https://your-backend-domain.com
PORT=8001
```

`BOT_SHARED_SECRET` must be the same as Java backend `TELEGRAM_BOT_SECRET`.

## Run locally

```bash
python -m venv .venv
source .venv/bin/activate
pip install -r requirements.txt
export BOT_TOKEN=...
export BOT_SHARED_SECRET=change-me-long-random-secret
export JAVA_API_BASE_URL=http://localhost:8080
python main.py
```

## Run with Docker

```bash
docker build -t safetrip-telegram-bot .
docker run -p 8001:8001 \
  -e BOT_TOKEN=... \
  -e BOT_SHARED_SECRET=change-me-long-random-secret \
  -e JAVA_API_BASE_URL=https://your-backend-domain.com \
  safetrip-telegram-bot
```
