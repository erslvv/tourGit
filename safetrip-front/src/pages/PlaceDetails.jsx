import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import api from "../api/axios";
import Navbar from "../components/Navbar";
import { getApiErrorMessage } from "../utils/apiError";
import { formatPrice, formatRating, getPlaceSubtitle, isFoodCategory } from "../utils/format";
import { isAuthenticated } from "../utils/auth";
import { applyImageFallback } from "../utils/images";
import "./Explore.css";

function PlaceDetails() {
  const { id } = useParams();
  const [place, setPlace] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [favoriteLoading, setFavoriteLoading] = useState(false);
  const [isFavorite, setIsFavorite] = useState(false);
  const [favoriteMessage, setFavoriteMessage] = useState("");

  useEffect(() => {
    const fetchPlace = async () => {
      try {
        const [{ data: placeData }, favoritesResponse] = await Promise.all([
          api.get(`/api/places/${id}`),
          isAuthenticated() ? api.get("/api/profile/favorites") : Promise.resolve({ data: null }),
        ]);

        setPlace(placeData);

        if (favoritesResponse.data) {
          const favorite = favoritesResponse.data.places.find((item) => item.placeId === Number(id));
          setIsFavorite(Boolean(favorite));
        }
      } catch (err) {
        setError(getApiErrorMessage(err, "Failed to load place details."));
      } finally {
        setLoading(false);
      }
    };

    fetchPlace();
  }, [id]);

  const toggleFavorite = async () => {
    if (!isAuthenticated()) {
      setFavoriteMessage("Login first to save this place in favorites.");
      return;
    }

    setFavoriteLoading(true);
    setFavoriteMessage("");

    try {
      if (isFavorite) {
        await api.delete(`/api/profile/favorites/places/${id}`);
        setIsFavorite(false);
        setFavoriteMessage("Place removed from favorites.");
      } else {
        await api.post(`/api/profile/favorites/places/${id}`);
        setIsFavorite(true);
        setFavoriteMessage("Place added to favorites.");
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
          <div className="explore-feedback">Loading place details...</div>
        </div>
      </div>
    );
  }

  if (error || !place) {
    return (
      <div className="details-page">
        <Navbar />
        <div className="details-shell">
          <div className="explore-feedback">{error || "Place not found."}</div>
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
            src={place.imageUrl || "https://via.placeholder.com/1200x600?text=Place"}
            alt={place.title}
            onError={(event) =>
              applyImageFallback(
                event,
                isFoodCategory(place.category)
                  ? "https://images.unsplash.com/photo-1414235077428-338989a2e8c0?auto=format&fit=crop&w=1400&q=80"
                  : "https://images.unsplash.com/photo-1512453979798-5ea266f8880c?auto=format&fit=crop&w=1400&q=80"
              )
            }
          />
        </div>

        <div className="details-content">
          <section className="details-main">
            <p className="details-main__eyebrow">{getPlaceSubtitle(place)}</p>
            <h1>{place.title}</h1>
            <p className="details-description">
              {place.description || "Detailed description is not available yet."}
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

              <Link className="explore-link" to={isFoodCategory(place.category) ? "/food" : "/entertainment"}>
                Back to section
              </Link>
            </div>

            {favoriteMessage ? <p className="explore-note">{favoriteMessage}</p> : null}
          </section>

          <aside className="details-side">
            <dl className="details-list">
              <div>
                <dt>Category</dt>
                <dd>{place.category || "Not specified"}</dd>
              </div>
              <div>
                <dt>City</dt>
                <dd>{place.city || "Almaty"}</dd>
              </div>
              <div>
                <dt>Average price</dt>
                <dd>{formatPrice(place.averagePrice)}</dd>
              </div>
              <div>
                <dt>Rating</dt>
                <dd>{formatRating(place.rating)}</dd>
              </div>
              <div>
                <dt>Coordinates</dt>
                <dd>
                  {place.latitude}, {place.longitude}
                </dd>
              </div>
              <div>
                <dt>Verified</dt>
                <dd>{place.isVerified ? "Yes" : "No"}</dd>
              </div>
            </dl>
          </aside>
        </div>
      </div>
    </main>
  );
}

export default PlaceDetails;
