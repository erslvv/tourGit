import { Link } from "react-router-dom";
import { clearAuth, getCurrentUser } from "../utils/auth";
import "./Navbar.css";

function Navbar() {
  const user = getCurrentUser();

  const handleLogout = () => {
    clearAuth();
    window.location.href = "/";
  };

  return (
    <header className="navbar">
      <Link to="/" className="navbar__logo">
        SafeTrip Almaty
      </Link>

      <nav className="navbar__links">
        <Link to="/">Home</Link>
        <Link to="/tours">Tours</Link>
        <Link to="/food">Food</Link>
        <Link to="/entertainment">Entertainment</Link>
        <Link to="/security">Security</Link>
      </nav>

      <div className="navbar__auth">
        {user ? (
          <>
            <div className="navbar__user">
              <span>{user.email}</span>
              <small>{user.role}</small>
            </div>
            <button type="button" className="btn btn--solid navbar__logout" onClick={handleLogout}>
              Logout
            </button>
          </>
        ) : (
          <>
            <Link to="/login" className="btn btn--ghost">
              Login
            </Link>
            <Link to="/register" className="btn btn--solid">
              Register
            </Link>
          </>
        )}
      </div>
    </header>
  );
}

export default Navbar;
