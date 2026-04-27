import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import api from "../api/axios";
import Navbar from "../components/Navbar";
import { getApiErrorMessage } from "../utils/apiError";
import { formatPrice, formatRating } from "../utils/format";
import { isAuthenticated } from "../utils/auth";
import { getTourExternalLink } from "../utils/externalLinks";
import { applyImageFallback } from "../utils/images";
import "./Explore.css";

function TourDetails() {
  const { id } = useParams();
  const [tour, setTour] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [favoriteLoading, setFavoriteLoading] = useState(false);
  const [isFavorite, setIsFavorite] = useState(false);
  const [favoriteMessage, setFavoriteMessage] = useState("");
  const instagramLink = getTourExternalLink(tour?.title);

  useEffect(() => {
    const fetchTour = async () => {
      try {
        const [{ data: tourData }, favoritesResponse] = await Promise.all([
          api.get(`/api/tours/${id}`),
          isAuthenticated() ? api.get("/api/profile/favorites") : Promise.resolve({ data: null }),
        ]);

        setTour(tourData);

        if (favoritesResponse.data) {
          const favorite = favoritesResponse.data.tours.find((item) => item.tourId === Number(id));
          setIsFavorite(Boolean(favorite));
        }
      } catch (err) {
        setError(getApiErrorMessage(err, "Failed to load tour details."));
      } finally {
        setLoading(false);
      }
    };

    fetchTour();
  }, [id]);

  const toggleFavorite = async () => {
    if (!isAuthenticated()) {
      setFavoriteMessage("Login first to save this tour in favorites.");
      return;
    }

    setFavoriteLoading(true);
    setFavoriteMessage("");

    try {
      if (isFavorite) {
        await api.delete(`/api/profile/favorites/tours/${id}`);
        setIsFavorite(false);
        setFavoriteMessage("Tour removed from favorites.");
      } else {
        await api.post(`/api/profile/favorites/tours/${id}`);
        setIsFavorite(true);
        setFavoriteMessage("Tour added to favorites.");
      }
    } catch (err) {
      setFavoriteMessage(getApiErrorMessage(err, "Favorite action failed."));
    } finally {
      setFavoriteLoading(false);
    }
  };

  if (loading) {
    return (
      <div className="details-page">
        <Navbar />
        <div className="details-shell">
          <div className="explore-feedback">Loading tour details...</div>
        </div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="details-page">
        <Navbar />
        <div className="details-shell">
          <div className="explore-feedback">{error}</div>
        </div>
      </div>
    );
  }

  if (!tour) {
    return (
      <div className="details-page">
        <Navbar />
        <div className="details-shell">
          <div className="explore-feedback">Tour not found.</div>
        </div>
      </div>
    );
  }

  return (
    <main className="details-page">
      <Navbar />

      <div className="details-shell">
        <div className="details-banner">
          <img
            src={tour.imageUrl || "https://via.placeholder.com/1200x600?text=Tour"}
            alt={tour.title}
            onError={(event) =>
              applyImageFallback(
                event,
                "https://images.unsplash.com/photo-1500534314209-a25ddb2bd429?auto=format&fit=crop&w=1400&q=80"
              )
            }
          />
        </div>

        <div className="details-content">
          <section className="details-main">
            <p className="details-main__eyebrow">{tour.city}</p>
            <h1>{tour.title}</h1>
            <p className="details-description">
              {tour.description || "Detailed description is not available yet."}
            </p>

            <div className="details-actions">
              <button
                className="explore-button"
                type="button"
                onClick={toggleFavorite}
                disabled={favoriteLoading}
              >
                {favoriteLoading
                  ? "Updating..."
                  : isFavorite
                    ? "Remove from favorites"
                    : "Save to favorites"}
              </button>

              <Link className="explore-link" to="/tours">
                Back to tours
              </Link>

              {instagramLink ? (
                <a
                  className="explore-link explore-link--external"
                  href={instagramLink}
                  target="_blank"
                  rel="noreferrer"
                >
                  Open Instagram
                </a>
              ) : (
                <p className="explore-inline-note">Add Instagram link in `externalLinks.js`.</p>
              )}
            </div>

            {favoriteMessage ? <p className="explore-note">{favoriteMessage}</p> : null}
          </section>

          <aside className="details-side">
            <dl className="details-list">
              <div>
                <dt>City</dt>
                <dd>{tour.city}</dd>
              </div>
              <div>
                <dt>Price</dt>
                <dd>{formatPrice(tour.price)}</dd>
              </div>
              <div>
                <dt>Rating</dt>
                <dd>{formatRating(tour.rating)}</dd>
              </div>
              <div>
                <dt>Duration</dt>
                <dd>{tour.durationDays ? `${tour.durationDays} day(s)` : "Flexible"}</dd>
              </div>
              <div>
                <dt>Verified</dt>
                <dd>{tour.isVerified ? "Yes" : "No"}</dd>
              </div>
              <div>
                <dt>Featured</dt>
                <dd>{tour.isFeatured ? "Yes" : "No"}</dd>
              </div>
              <div>
                <dt>Coordinates</dt>
                <dd>
                  {tour.startLat && tour.startLng ? `${tour.startLat}, ${tour.startLng}` : "Not specified"}
                </dd>
              </div>
            </dl>
          </aside>
        </div>
      </div>
    </main>
  );
}

export default TourDetails;
