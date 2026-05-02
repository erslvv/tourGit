import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import api from "../api/axios";
import { getApiErrorMessage } from "../utils/apiError";
import "./Auth.css";

function ForgotPassword() {
  const navigate = useNavigate();

  const [email, setEmail] = useState("");
  const [otp, setOtp] = useState("");
  const [otpRequested, setOtpRequested] = useState(false);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const handleRequestOtp = async (event) => {
    event.preventDefault();

    setLoading(true);
    setError("");
    setSuccess("");

    try {
      await api.post("/api/auth/forgot-password", {
        email: email.trim().toLowerCase(),
      });

      setOtp("");
      setOtpRequested(true);
      setSuccess("If this account has Telegram connected, a 6-digit reset code was sent to the bot.");
    } catch (err) {
      setError(getApiErrorMessage(err, "Failed to request password reset code."));
    } finally {
      setLoading(false);
    }
  };

  const handleVerifyOtp = async (event) => {
    event.preventDefault();

    if (!/^[0-9]{6}$/.test(otp)) {
      setError("Enter the 6-digit Telegram code.");
      return;
    }

    setLoading(true);
    setError("");
    setSuccess("");

    try {
      const { data } = await api.post("/api/auth/forgot-password/verify-otp", {
        email: email.trim().toLowerCase(),
        otp: otp.trim(),
      });

      setSuccess("Code verified. Opening password reset page...");

      setTimeout(() => {
        navigate(`/reset-password?token=${encodeURIComponent(data.resetToken)}`);
      }, 400);
    } catch (err) {
      setError(getApiErrorMessage(err, "Invalid or expired code."));
    } finally {
      setLoading(false);
    }
  };

  return (
      <main className="auth-page">
        <div className="auth-layout">
          <section className="auth-hero">
            <span className="auth-hero__badge">Account recovery</span>

            <div className="auth-hero__content">
              <h1>Reset access by Telegram.</h1>
              <p>
                Enter your email. SafeTrip sends a one-time code to the Telegram account linked during registration.
              </p>
            </div>

            <div className="auth-hero__stats">
              <div>
                <strong>Telegram</strong>
                <span>Code goes to bot</span>
              </div>
              <div>
                <strong>6 digits</strong>
                <span>OTP code</span>
              </div>
              <div>
                <strong>5 min</strong>
                <span>Default expiration</span>
              </div>
            </div>
          </section>

          <section className="auth-card">
            <p className="auth-card__eyebrow">Forgot password</p>

            <h2>{otpRequested ? "Enter Telegram code" : "Request Telegram code"}</h2>

            <p className="auth-card__text">
              {otpRequested
                  ? "Check the SafeTrip Telegram bot and enter the 6-digit code."
                  : "The email must belong to an account that has Telegram connected."}
            </p>

            {error ? <div className="auth-card__error">{error}</div> : null}
            {success ? <div className="auth-card__success">{success}</div> : null}

            {!otpRequested ? (
                <form className="auth-form" onSubmit={handleRequestOtp}>
                  <label>
                    Email
                    <input
                        type="email"
                        name="email"
                        placeholder="you@example.com"
                        value={email}
                        onChange={(event) => setEmail(event.target.value)}
                        required
                    />
                  </label>

                  <button type="submit" disabled={loading}>
                    {loading ? "Sending..." : "Send Telegram code"}
                  </button>
                </form>
            ) : (
                <form className="auth-form" onSubmit={handleVerifyOtp}>
                  <label>
                    Email
                    <input type="email" value={email} readOnly />
                  </label>

                  <label>
                    Telegram code
                    <input
                        type="text"
                        inputMode="numeric"
                        autoComplete="one-time-code"
                        pattern="[0-9]{6}"
                        maxLength={6}
                        placeholder="123456"
                        value={otp}
                        onChange={(event) => {
                          setOtp(event.target.value.replace(/\D/g, "").slice(0, 6));
                        }}
                        required
                    />
                  </label>

                  <button type="submit" disabled={loading || otp.length !== 6}>
                    {loading ? "Checking..." : "Verify code"}
                  </button>

                  <button type="button" disabled={loading} onClick={handleRequestOtp}>
                    Send new code
                  </button>
                </form>
            )}

            <p className="auth-card__footer">
              Remembered your password? <Link to="/login">Back to login</Link>.
            </p>
          </section>
        </div>
      </main>
  );
}

export default ForgotPassword;