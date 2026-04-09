import { Link } from "react-router-dom";
import Navbar from "../components/Navbar";
import "./Home.css";

function Home() {
  return (
    <div className="home">
      <Navbar />

      <section className="hero">
        <div className="hero__overlay"></div>

        <div className="hero__content">
          <p className="hero__subtitle">Trusted Travel Platform for First-Time Visitors</p>

          <h1>
            Welcome to <br />
            Almaty
          </h1>

          <p className="hero__text">
            Explore Kazakhstan safely with verified tours, trusted places, and useful local
            information in one platform.
          </p>

          <div className="hero__buttons">
            <Link to="/tours" className="hero__btn hero__btn--primary">
              Explore Tours
            </Link>

            <Link to="/register" className="hero__btn hero__btn--secondary">
              Get Started
            </Link>
          </div>
        </div>
      </section>

      <section className="featured">
        <div className="featured__header">
          <p className="featured__label">Popular destinations</p>
          <h2>Featured Tours</h2>
          <p className="featured__text">
            Discover some of the most exciting places near Almaty for first-time visitors.
          </p>
        </div>

        <div className="cards">
          <div className="card">
            <img
              src="https://images.unsplash.com/photo-1500530855697-b586d89ba3ee?auto=format&fit=crop&w=900&q=80"
              alt="Mountain Tour"
            />
            <div className="card__content">
              <h3>Mountain Tour</h3>
              <p>Explore beautiful mountain landscapes near Almaty with a trusted local guide.</p>
              <Link to="/tours/1" className="card__link">
                View More
              </Link>
            </div>
          </div>

          <div className="card">
            <img
              src="https://images.unsplash.com/photo-1506744038136-46273834b3fb?auto=format&fit=crop&w=900&q=80"
              alt="Kolsai Lakes"
            />
            <div className="card__content">
              <h3>Kolsai Lakes</h3>
              <p>Visit one of the most beautiful natural destinations in Kazakhstan.</p>
              <Link to="/tours/1" className="card__link">
                View More
              </Link>
            </div>
          </div>

          <div className="card">
            <img
              src="https://images.unsplash.com/photo-1512453979798-5ea266f8880c?auto=format&fit=crop&w=900&q=80"
              alt="City Tour"
            />
            <div className="card__content">
              <h3>City Tour</h3>
              <p>Discover Almaty city highlights, culture, local food, and iconic locations.</p>
              <Link to="/tours/1" className="card__link">
                View More
              </Link>
            </div>
          </div>
        </div>
      </section>
    </div>
  );
}

export default Home;