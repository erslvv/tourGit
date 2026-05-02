import logging
import os
from contextlib import asynccontextmanager

import httpx
import uvicorn
from fastapi import FastAPI, Header, HTTPException, Request
from pydantic import BaseModel
from telegram import Update
from telegram.ext import Application, CommandHandler, ContextTypes, MessageHandler, filters

logging.basicConfig(
    level=logging.INFO,
    format="%(asctime)s [%(levelname)s] %(name)s: %(message)s",
)

logger = logging.getLogger("safetrip-telegram-bot")

BOT_TOKEN = os.getenv("BOT_TOKEN", "").strip()
JAVA_API_BASE_URL = os.getenv("JAVA_API_BASE_URL", "http://localhost:8080").rstrip("/")
BOT_SHARED_SECRET = os.getenv("BOT_SHARED_SECRET", "change-me-local-secret")
PORT = int(os.getenv("PORT", "8001"))

if not BOT_TOKEN:
    raise RuntimeError("BOT_TOKEN environment variable is required")

telegram_app: Application | None = None


class SendMessageRequest(BaseModel):
    chatId: str | None = None
    chat_id: str | None = None
    telegramChatId: str | None = None
    telegram_chat_id: str | None = None
    text: str | None = None
    message: str | None = None
    body: str | None = None


def extract_bind_code(payload: str) -> str | None:
    if payload is None:
        return None

    value = payload.strip()

    if not value:
        return None

    if value.startswith("/start"):
        parts = value.split(maxsplit=1)
        if len(parts) < 2:
            return None
        value = parts[1].strip()

    if value.startswith("bind_"):
        value = value.replace("bind_", "", 1).strip()

    if not value:
        return None

    return value


async def bind_with_payload(update: Update, payload: str) -> None:
    if update.effective_chat is None or update.effective_user is None or update.message is None:
        return

    bind_code = extract_bind_code(payload)

    if not bind_code:
        await update.message.reply_text(
            "Invalid token.\n\n"
            "Open SafeTrip, generate a Telegram connection token, copy it and send it here.\n\n"
            "Example:\n"
            "bind_xxxxxxxx"
        )
        return

    user = update.effective_user

    request_body = {
        "bindCode": bind_code,
        "chatId": str(update.effective_chat.id),
        "username": user.username,
        "firstName": user.first_name,
        "lastName": user.last_name,
    }

    try:
        async with httpx.AsyncClient(timeout=10) as client:
            response = await client.post(
                f"{JAVA_API_BASE_URL}/api/internal/telegram/bind",
                headers={"X-Bot-Secret": BOT_SHARED_SECRET},
                json=request_body,
            )

        if response.status_code < 200 or response.status_code >= 300:
            logger.warning(
                "Java bind failed: status=%s body=%s",
                response.status_code,
                response.text,
            )
            await update.message.reply_text(
                "Could not connect Telegram.\n\n"
                "Generate a new token in SafeTrip and send it here again."
            )
            return

        data = response.json()

        await update.message.reply_text(
            f"Telegram connected successfully for {data.get('email', 'your SafeTrip account')}.\n\n"
            "You can now use this bot to receive password reset codes."
        )
    except Exception:
        logger.exception("Failed to bind Telegram account")
        await update.message.reply_text("Temporary error. Try again later.")


async def start(update: Update, context: ContextTypes.DEFAULT_TYPE) -> None:
    if update.effective_chat is None or update.effective_user is None or update.message is None:
        return

    args = context.args or []

    if args:
        await bind_with_payload(update, args[0])
        return

    await update.message.reply_text(
        "SafeTrip recovery bot.\n\n"
        "To connect Telegram:\n"
        "1. Open SafeTrip.\n"
        "2. Generate a Telegram connection token.\n"
        "3. Copy the token and send it here.\n\n"
        "Example:\n"
        "bind_xxxxxxxx"
    )


async def bind_token_message(update: Update, context: ContextTypes.DEFAULT_TYPE) -> None:
    if update.message is None or update.message.text is None:
        return

    await bind_with_payload(update, update.message.text)


@asynccontextmanager
async def lifespan(app: FastAPI):
    global telegram_app

    telegram_app = Application.builder().token(BOT_TOKEN).build()

    telegram_app.add_handler(CommandHandler("start", start))
    telegram_app.add_handler(MessageHandler(filters.TEXT & ~filters.COMMAND, bind_token_message))

    await telegram_app.initialize()
    await telegram_app.start()
    await telegram_app.updater.start_polling()

    logger.info("Telegram polling started")

    try:
        yield
    finally:
        logger.info("Stopping Telegram polling")

        if telegram_app.updater:
            await telegram_app.updater.stop()

        await telegram_app.stop()
        await telegram_app.shutdown()


app = FastAPI(title="SafeTrip Telegram Bot", lifespan=lifespan)


@app.get("/health")
async def health():
    return {"status": "ok"}


@app.post("/api/messages")
async def send_message(
    request: Request,
    x_bot_secret: str | None = Header(default=None, alias="X-Bot-Secret"),
):
    if not x_bot_secret or x_bot_secret != BOT_SHARED_SECRET:
        raise HTTPException(status_code=401, detail="Invalid bot secret")

    if telegram_app is None:
        raise HTTPException(status_code=503, detail="Telegram bot is not ready")

    try:
        body = await request.json()
    except Exception as exc:
        raise HTTPException(status_code=400, detail="Invalid JSON body") from exc

    logger.info("Received /api/messages body: %s", body)

    chat_id = (
        body.get("chatId")
        or body.get("chat_id")
        or body.get("telegramChatId")
        or body.get("telegram_chat_id")
    )

    text = (
        body.get("text")
        or body.get("message")
        or body.get("body")
    )

    if not chat_id:
        raise HTTPException(
            status_code=400,
            detail="chatId is required. Supported fields: chatId, chat_id, telegramChatId, telegram_chat_id",
        )

    if not text:
        raise HTTPException(
            status_code=400,
            detail="text is required. Supported fields: text, message, body",
        )

    try:
        await telegram_app.bot.send_message(
            chat_id=str(chat_id),
            text=str(text),
        )

        logger.info("Telegram message sent to chat_id=%s", chat_id)

        return {"status": "sent"}
    except Exception as exc:
        logger.exception("Failed to send Telegram message")
        raise HTTPException(status_code=500, detail=str(exc)) from exc


if __name__ == "__main__":
    uvicorn.run("main:app", host="0.0.0.0", port=PORT)