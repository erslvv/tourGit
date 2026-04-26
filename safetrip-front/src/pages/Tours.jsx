import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import api from "../api/axios";
import Navbar from "../components/Navbar";
import { getApiErrorMessage } from "../utils/apiError";
import { formatPrice, formatRating } from "../utils/format";
import { applyImageFallback } from "../utils/images";
import bigAlmatyLakeImage from "../assets/images/bal.jpg";
import charynImage from "../assets/images/charyn.jpg";
import "./Explore.css";

function Tours() {
  const [tours, setTours] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchTours = async () => {
      try {
        const response = await api.get("/api/tours");
        setTours(response.data);
      } catch (err) {
        setError(getApiErrorMessage(err, "Failed to load tours."));
      } finally {
        setLoading(false);
      }
    };

    fetchTours();
  }, []);

  if (loading) {
    return (
      <div className="explore-page">
        <Navbar />
        <div className="explore-shell">
          <div className="explore-feedback">Loading tours...</div>
        </div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="explore-page">
        <Navbar />
        <div className="explore-shell">
          <div className="explore-feedback">{error}</div>
        </div>
      </div>
    );
  }

  return (
    <main className="explore-page">
      <Navbar />

      <div className="explore-shell">
        <section className="explore-hero explore-hero--tours">
          <span className="explore-hero__label">Tours around Almaty</span>
          <h1>Explore verified routes and open each card for full details.</h1>
          <p>
            This section is connected to the backend `GET /api/tours` endpoint and gives the user a
            clear catalog of tourist routes before they open a specific destination.
          </p>

          <div className="explore-stats">
            <div>
              <strong>{tours.length}</strong>
              <span>Available tours</span>
            </div>
            <div>
              <strong>{tours.filter((tour) => tour.isVerified).length}</strong>
              <span>Verified options</span>
            </div>
            <div>
              <strong>{tours.filter((tour) => tour.isFeatured).length}</strong>
              <span>Featured picks</span>
            </div>
          </div>
        </section>

        <div className="explore-toolbar">
          <div>
            <h2>Tour Cards</h2>
            <p>Compare route, city, duration, price, and then open the full card with details.</p>
          </div>
        </div>

        <div className="explore-grid">
          {tours.length > 0 ? (
            tours.map((tour) => (
              <article className="explore-card" key={tour.id}>
                <img
                  src={
                    tour.title === "Big Almaty Lake Day Tour"
                      ? bigAlmatyLakeImage
                      : tour.title === "Charyn Canyon Adventure"
                      ? charynImage
                      : tour.imageUrl || "https://via.placeholder.com/400x250?text=Tour"
                  }
                  alt={tour.title}
                  onError={(event) =>
                    applyImageFallback(
                      event,
                      "https://images.unsplash.com/photo-1500534314209-a25ddb2bd429?auto=format&fit=crop&w=1200&q=80"
                    )
                  }
                />

                <div className="explore-card__body">
                  <p className="explore-card__meta">{tour.city}</p>
                  <h3>{tour.title}</h3>
                  <p className="explore-card__text">
                    {tour.description || "No description available."}
                  </p>

                  <div className="explore-card__info">
                    <span className="explore-chip">{formatPrice(tour.price)}</span>
                    <span className="explore-chip">Rating {formatRating(tour.rating)}</span>
                    <span className="explore-chip">
                      {tour.durationDays ? `${tour.durationDays} day(s)` : "Flexible duration"}
                    </span>
                  </div>

                  <div className="explore-card__actions">
                    <Link to={`/tours/${tour.id}`} className="explore-link">
                      View Details
                    </Link>
                  </div>
                </div>
              </article>
            ))
          ) : (
            <div className="explore-empty">No tours found.</div>
          )}
        </div>
      </div>
    </main>
  );
}

export default Tours;
