import { useMemo, useState } from "react";
import { Link, useNavigate, useSearchParams } from "react-router-dom";
import api from "../api/axios";
import { getApiErrorMessage } from "../utils/apiError";
import "./Auth.css";

function ResetPassword() {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const token = useMemo(() => searchParams.get("token") || "", [searchParams]);
  const [form, setForm] = useState({
    password: "",
    confirmPassword: "",
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const handleChange = (event) => {
    const { name, value } = event.target;
    setForm((current) => ({ ...current, [name]: value }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    setLoading(true);
    setError("");
    setSuccess("");

    if (!token) {
      setLoading(false);
      setError("Reset token is missing from the link.");
      return;
    }

    if (form.password !== form.confirmPassword) {
      setLoading(false);
      setError("Passwords do not match.");
      return;
    }

    try {
      await api.post("/api/auth/reset-password", {
        token,
        newPassword: form.password,
      });

      setSuccess("Password has been changed. Redirecting to login...");
      setTimeout(() => navigate("/login"), 900);
    } catch (err) {
      setError(getApiErrorMessage(err, "Failed to reset password."));
    } finally {
      setLoading(false);
    }
  };

  return (
    <main className="auth-page">
      <div className="auth-layout">
        <section className="auth-hero">
          <span className="auth-hero__badge">New password</span>

          <div className="auth-hero__content">
            <h1>Create a new password.</h1>
            <p>
              This page reads the token from the email link and sends the new password to the
              backend reset endpoint.
            </p>
          </div>

          <div className="auth-hero__stats">
            <div>
              <strong>One use</strong>
              <span>Token is consumed</span>
            </div>
            <div>
              <strong>BCrypt</strong>
              <span>Password is hashed</span>
            </div>
            <div>
              <strong>Login</strong>
              <span>Use new password</span>
            </div>
          </div>
        </section>

        <section className="auth-card">
          <p className="auth-card__eyebrow">Reset password</p>
          <h2>Set new password</h2>
          <p className="auth-card__text">
            Enter and confirm your new password.
          </p>

          {!token ? <div className="auth-card__error">Reset token is missing from the URL.</div> : null}
          {error ? <div className="auth-card__error">{error}</div> : null}
          {success ? <div className="auth-card__success">{success}</div> : null}

          <form className="auth-form" onSubmit={handleSubmit}>
            <label>
              New password
              <input
                type="password"
                name="password"
                placeholder="Create new password"
                value={form.password}
                onChange={handleChange}
                required
                minLength={6}
              />
            </label>

            <label>
              Confirm password
              <input
                type="password"
                name="confirmPassword"
                placeholder="Repeat new password"
                value={form.confirmPassword}
                onChange={handleChange}
                required
                minLength={6}
              />
            </label>

            <button type="submit" disabled={loading || !token}>
              {loading ? "Saving..." : "Reset password"}
            </button>
          </form>

          <p className="auth-card__footer">
            Already changed it? <Link to="/login">Go to login</Link>.
          </p>
        </section>
      </div>
    </main>
  );
}

export default ResetPassword;
