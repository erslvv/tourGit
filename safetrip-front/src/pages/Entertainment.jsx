import { useEffect, useMemo, useState } from "react";
import { Link } from "react-router-dom";
import api from "../api/axios";
import Navbar from "../components/Navbar";
import { getApiErrorMessage } from "../utils/apiError";
import { formatPrice, formatRating, getPlaceSubtitle, isFoodCategory } from "../utils/format";
import { getPlaceExternalLink } from "../utils/externalLinks";
import { applyImageFallback } from "../utils/images";
import "./Explore.css";

function Entertainment() {
  const [places, setPlaces] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchPlaces = async () => {
      try {
        const { data } = await api.get("/api/places");
        setPlaces(data);
      } catch (err) {
        setError(getApiErrorMessage(err, "Failed to load entertainment places."));
      } finally {
        setLoading(false);
      }
    };

    fetchPlaces();
  }, []);

  const entertainmentPlaces = useMemo(
    () => places.filter((place) => !isFoodCategory(place.category)),
    [places]
  );

  if (loading) {
    return (
      <div className="explore-page">
        <Navbar />
        <div className="explore-shell">
          <div className="explore-feedback">Loading entertainment places...</div>
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
        <section className="explore-hero explore-hero--entertainment">
          <span className="explore-hero__label">Entertainment in Almaty</span>
          <h1>Choose viewpoints, cultural spots, and city experiences after the main route.</h1>
          <p>
            This section is built from backend places as well, but filtered into non-food spots so
            tourists can discover where to go next.
          </p>

          <div className="explore-stats">
            <div>
              <strong>{entertainmentPlaces.length}</strong>
              <span>Entertainment locations</span>
            </div>
            <div>
              <strong>{entertainmentPlaces.filter((place) => place.isVerified).length}</strong>
              <span>Verified picks</span>
            </div>
            <div>
              <strong>{entertainmentPlaces.filter((place) => place.isFeatured).length}</strong>
              <span>Featured picks</span>
            </div>
          </div>
        </section>

        <div className="explore-toolbar">
          <div>
            <h2>Entertainment Cards</h2>
            <p>Open a card to see location details, average price, and place description.</p>
          </div>
        </div>

        <div className="explore-grid">
          {entertainmentPlaces.length ? (
            entertainmentPlaces.map((place) => (
              <article className="explore-card" key={place.id}>
                <img
                  src={place.imageUrl || "https://via.placeholder.com/400x250?text=Entertainment"}
                  alt={place.title}
                  onError={(event) =>
                    applyImageFallback(
                      event,
                      "https://images.unsplash.com/photo-1512453979798-5ea266f8880c?auto=format&fit=crop&w=1200&q=80"
                    )
                  }
                />

                <div className="explore-card__body">
                  {(() => {
                    const mapLink = getPlaceExternalLink(place.title);

                    return (
                      <>
                  <p className="explore-card__meta">{getPlaceSubtitle(place)}</p>
                  <h3>{place.title}</h3>
                  <p className="explore-card__text">
                    {place.description || "No description available."}
                  </p>

                  <div className="explore-card__info">
                    <span className="explore-chip">{place.category || "Place"}</span>
                    <span className="explore-chip">Rating {formatRating(place.rating)}</span>
                    <span className="explore-chip">{formatPrice(place.averagePrice)}</span>
                  </div>

                  <div className="explore-card__actions">
                    <Link className="explore-link" to={`/places/${place.id}`}>
                      View Details
                    </Link>

                    {mapLink ? (
                      <a
                        className="explore-button"
                        href={mapLink}
                        target="_blank"
                        rel="noreferrer"
                      >
                        Open in 2GIS
                      </a>
                    ) : (
                      <span className="explore-inline-note">Add 2GIS link later</span>
                    )}
                  </div>
                      </>
                    );
                  })()}
                </div>
              </article>
            ))
          ) : (
            <div className="explore-empty">No entertainment places found.</div>
          )}
        </div>
      </div>
    </main>
  );
}

export default Entertainment;
