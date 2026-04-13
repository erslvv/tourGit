import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import api from "../api/axios";
import { saveAuth } from "../utils/auth";
import "./Auth.css";

function Login() {
  const navigate = useNavigate();
  const [form, setForm] = useState({
    email: "",
    password: "",
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const handleChange = (event) => {
    const { name, value } = event.target;
    setForm((current) => ({ ...current, [name]: value }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    setLoading(true);
    setError("");

    try {
      const { data } = await api.post("/api/auth/login", {
        email: form.email.trim().toLowerCase(),
        password: form.password,
      });

      saveAuth(data);
      navigate("/tours");
    } catch (err) {
      setError(
        err.response?.data?.message ||
          err.response?.data?.error ||
          "Login failed. Check your email and password."
      );
    } finally {
      setLoading(false);
    }
  };

  return (
    <main className="auth-page">
      <div className="auth-layout">
        <section className="auth-hero">
          <span className="auth-hero__badge">SafeTrip Almaty</span>

          <div className="auth-hero__content">
            <h1>Welcome back to your Almaty guide.</h1>
            <p>
              Sign in to keep your travel flow in one place, save favorites later, and continue
              exploring tours, food spots, and entertainment around the city.
            </p>
          </div>

          <div className="auth-hero__stats">
            <div>
              <strong>Tours</strong>
              <span>Open routes and details</span>
            </div>
            <div>
              <strong>Food</strong>
              <span>Trusted places to eat</span>
            </div>
            <div>
              <strong>Security</strong>
              <span>Important local guidance</span>
            </div>
          </div>
        </section>

        <section className="auth-card">
          <p className="auth-card__eyebrow">Login</p>
          <h2>Sign in</h2>
          <p className="auth-card__text">
            Use your SafeTrip account to connect the frontend with the real backend flow.
          </p>

          {error ? <div className="auth-card__error">{error}</div> : null}

          <form className="auth-form" onSubmit={handleSubmit}>
            <label>
              Email
              <input
                type="email"
                name="email"
                placeholder="demo@safetrip.kz"
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
                placeholder="Enter your password"
                value={form.password}
                onChange={handleChange}
                required
              />
            </label>

            <button type="submit" disabled={loading}>
              {loading ? "Signing in..." : "Login"}
            </button>
          </form>

          <p className="auth-card__footer">
            Don&apos;t have an account? <Link to="/register">Create one here</Link>.
          </p>
        </section>
      </div>
    </main>
  );
}

export default Login;
