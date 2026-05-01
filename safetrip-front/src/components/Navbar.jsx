import { useState } from "react";
import { Link } from "react-router-dom";
import { clearAuth, getCurrentUser, isAdminUser } from "../utils/auth";
import "./Navbar.css";

function Navbar() {
  const user = getCurrentUser();
  const isAdmin = isAdminUser();
  const [menuOpen, setMenuOpen] = useState(false);

  const handleLogout = () => {
    clearAuth();
    window.location.href = "/";
  };

  const closeMenu = () => {
    setMenuOpen(false);
  };

  return (
    <header className="navbar">
      <Link to="/" className="navbar__logo">
        SafeTrip
      </Link>

      <button
        type="button"
        className={`navbar__toggle${menuOpen ? " navbar__toggle--open" : ""}`}
        onClick={() => setMenuOpen((current) => !current)}
        aria-label="Toggle navigation"
        aria-expanded={menuOpen}
      >
        <span />
        <span />
        <span />
      </button>

      <nav className={`navbar__links${menuOpen ? " navbar__links--open" : ""}`}>
        <Link to="/" onClick={closeMenu}>Home</Link>
        <Link to="/tours" onClick={closeMenu}>Tours</Link>
        <Link to="/food" onClick={closeMenu}>Food</Link>
        <Link to="/entertainment" onClick={closeMenu}>Entertainment</Link>
        <Link to="/security" onClick={closeMenu}>Security</Link>
        {user ? <Link to="/profile" onClick={closeMenu}>Profile</Link> : null}
        {isAdmin ? <Link to="/admin" onClick={closeMenu}>Admin</Link> : null}
      </nav>

      <div className={`navbar__auth${menuOpen ? " navbar__auth--open" : ""}`}>
        {user ? (
          <>
            <div className="navbar__user">
              <span>{user.email}</span>
              <small>{user.role}</small>
            </div>
            <button
              type="button"
              className="btn btn--solid navbar__logout"
              onClick={handleLogout}
            >
              Logout
            </button>
          </>
        ) : (
          <>
            <Link to="/login" className="btn btn--ghost" onClick={closeMenu}>
              Login
            </Link>
            <Link to="/register" className="btn btn--solid" onClick={closeMenu}>
              Register
            </Link>
          </>
        )}
      </div>
    </header>
  );
}

export default Navbar;
