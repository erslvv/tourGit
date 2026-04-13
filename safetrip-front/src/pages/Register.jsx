import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import api from "../api/axios";
import { saveAuth } from "../utils/auth";
import "./Auth.css";

function Register() {
  const navigate = useNavigate();
  const [form, setForm] = useState({
    email: "",
    password: "",
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

    try {
      const { data } = await api.post("/api/auth/register", {
        email: form.email.trim().toLowerCase(),
        password: form.password,
      });

      saveAuth(data);
      setSuccess("Registration completed. Redirecting to tours...");
      setTimeout(() => navigate("/tours"), 700);
    } catch (err) {
      setError(
        err.response?.data?.message ||
          err.response?.data?.error ||
          "Registration failed. Please try again."
      );
    } finally {
      setLoading(false);
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
              Register once and move through Almaty with a clearer flow: open sections, compare
              cards, and later connect favorites and profile features from the backend.
            </p>
          </div>

          <div className="auth-hero__stats">
            <div>
              <strong>Home</strong>
              <span>First impression for tourists</span>
            </div>
            <div>
              <strong>Cards</strong>
              <span>Open details for each place</span>
            </div>
            <div>
              <strong>Profile</strong>
              <span>Ready for favorites integration</span>
            </div>
          </div>
        </section>

        <section className="auth-card">
          <p className="auth-card__eyebrow">Register</p>
          <h2>Create account</h2>
          <p className="auth-card__text">
            This form is connected to the backend endpoint `POST /api/auth/register`.
          </p>

          {error ? <div className="auth-card__error">{error}</div> : null}
          {success ? <div className="auth-card__success">{success}</div> : null}

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

          <p className="auth-card__footer">
            Already registered? <Link to="/login">Go to login</Link>.
          </p>
        </section>
      </div>
    </main>
  );
}

export default Register;
