import { useMemo, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import api from "../api/axios";
import Footer from "../components/Footer";
import { getApiErrorMessage } from "../utils/apiError";
import { saveAuth } from "../utils/auth";
import "./Auth.css";

function Register() {
  const navigate = useNavigate();

  const [form, setForm] = useState({
    email: "",
    password: "",
  });

  const [telegramLink, setTelegramLink] = useState(null);
  const [loading, setLoading] = useState(false);
  const [checking, setChecking] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const telegramToken = useMemo(() => {
    if (!telegramLink) {
      return "";
    }

    if (telegramLink.bindToken) {
      return telegramLink.bindToken.startsWith("bind_")
        ? telegramLink.bindToken
        : `bind_${telegramLink.bindToken}`;
    }

    if (telegramLink.bindCode) {
      return telegramLink.bindCode.startsWith("bind_")
        ? telegramLink.bindCode
        : `bind_${telegramLink.bindCode}`;
    }

    if (telegramLink.telegramStartUrl) {
      try {
        const url = new URL(telegramLink.telegramStartUrl);
        const start = url.searchParams.get("start");

        if (start) {
          return start.startsWith("bind_") ? start : `bind_${start}`;
        }
      } catch {
        return "";
      }
    }

    return "";
  }, [telegramLink]);

  const botUsername = telegramLink?.botUsername || "safetrip_kz_bot";

  const handleChange = (event) => {
    const { name, value } = event.target;
    setForm((current) => ({ ...current, [name]: value }));
  };

  const startTelegramBinding = async () => {
    const { data } = await api.post("/api/auth/telegram-link/start");
    setTelegramLink(data);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    setLoading(true);
    setError("");
    setSuccess("");

    try {
      const { data } = await api.post("/api/auth/register", {
        email: form.email.trim().toLowerCase(),
        password: form.password,
      });

      saveAuth(data);
      await startTelegramBinding();

      setSuccess("Account created. Now connect Telegram for password recovery.");
    } catch (err) {
      setError(getApiErrorMessage(err, "Registration failed. Please try again."));
    } finally {
      setLoading(false);
    }
  };

  const handleRefreshTelegramStatus = async () => {
    setChecking(true);
    setError("");
    setSuccess("");

    try {
      const { data } = await api.get("/api/auth/telegram-link/status");

      if (data.telegramVerified) {
        setSuccess("Telegram connected. Redirecting to tours...");

        setTimeout(() => {
          navigate("/tours");
        }, 700);

        return;
      }

      setError("Telegram is not connected yet. Copy the token and send it to the bot first.");
    } catch (err) {
      setError(getApiErrorMessage(err, "Failed to check Telegram status."));
    } finally {
      setChecking(false);
    }
  };

  const handleRegenerateTelegramLink = async () => {
    setChecking(true);
    setError("");
    setSuccess("");

    try {
      await startTelegramBinding();
      setSuccess("New Telegram token generated.");
    } catch (err) {
      setError(getApiErrorMessage(err, "Failed to generate Telegram token."));
    } finally {
      setChecking(false);
    }
  };

  const handleCopyToken = async () => {
    if (!telegramToken) {
      return;
    }

    try {
      await navigator.clipboard.writeText(telegramToken);
      setSuccess("Telegram token copied. Open the bot and send it as a message.");
      setError("");
    } catch {
      setError("Could not copy token automatically. Select and copy it manually.");
    }
  };

  return (
    <main className="auth-page">
      <div className="auth-layout">
        <section className="auth-hero">
          <span className="auth-hero__badge">Start your trip</span>

          <div className="auth-hero__content">
            <h1>Create your SafeTrip account.</h1>
            <p>
              Register once and connect Telegram. Telegram is used only for password recovery codes.
            </p>
          </div>

          <div className="auth-hero__stats">
            <div>
              <strong>Step 1</strong>
              <span>Create account</span>
            </div>
            <div>
              <strong>Step 2</strong>
              <span>Send token to bot</span>
            </div>
            <div>
              <strong>Recovery</strong>
              <span>Reset by Telegram OTP</span>
            </div>
          </div>
        </section>

        <section className="auth-card">
          <Link to="/" className="auth-back">
            Back to Home
          </Link>

          <p className="auth-card__eyebrow">Register</p>

          <h2>{telegramLink ? "Connect Telegram" : "Create account"}</h2>

          <p className="auth-card__text">
            {telegramLink
              ? "Copy the token below, open the Telegram bot, send the token as a message, then come back and check the status."
              : "Create an account first. After that the app will generate a Telegram connection token."}
          </p>

          {error ? <div className="auth-card__error">{error}</div> : null}
          {success ? <div className="auth-card__success">{success}</div> : null}

          {!telegramLink ? (
            <form className="auth-form" onSubmit={handleSubmit}>
              <label>
                Email
                <input
                  type="email"
                  name="email"
                  placeholder="you@example.com"
                  value={form.email}
                  onChange={handleChange}
                  required
                />
              </label>

              <label>
                Password
                <input
                  type="password"
                  name="password"
                  placeholder="Create a password"
                  value={form.password}
                  onChange={handleChange}
                  required
                  minLength={6}
                />
              </label>

              <button type="submit" disabled={loading}>
                {loading ? "Creating account..." : "Register"}
              </button>
            </form>
          ) : (
            <div className="auth-form">
              <label>
                Telegram recovery bot
                <input value={`@${botUsername}`} readOnly />
              </label>

              <label>
                Token to send to bot
                <input value={telegramToken} readOnly />
              </label>

              <button type="button" onClick={handleCopyToken} disabled={!telegramToken}>
                Copy Telegram token
              </button>

              <a
                className="auth-button"
                href={`https://t.me/${botUsername}`}
                target="_blank"
                rel="noreferrer"
              >
                Open Telegram bot
              </a>

              <button type="button" onClick={handleRefreshTelegramStatus} disabled={checking}>
                {checking ? "Checking..." : "I sent token to bot"}
              </button>

              <button type="button" onClick={handleRegenerateTelegramLink} disabled={checking}>
                Generate new Telegram token
              </button>
            </div>
          )}

          <p className="auth-card__footer">
            Already registered? <Link to="/login">Go to login</Link>.
          </p>
        </section>
      </div>

      <Footer />
    </main>
  );
}

export default Register;
