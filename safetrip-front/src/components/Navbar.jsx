import { Link } from "react-router-dom";
import "./Navbar.css";

function Navbar() {
  return (
    <header className="navbar">
      <div className="navbar__logo">SafeTrip KZ</div>

      <nav className="navbar__links">
        <Link to="/">Home</Link>
        <Link to="/tours">Tours</Link>
        <Link to="/food">Food</Link>
        <Link to="/entertainment">Entertainment</Link>
        <Link to="/security">Security</Link>
      </nav>

      <div className="navbar__auth">
        <Link to="/login" className="btn btn--ghost">
          Login
        </Link>
        <Link to="/register" className="btn btn--solid">
          Register
        </Link>
      </div>
    </header>
  );
}

export default Navbar;