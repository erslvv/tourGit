import { useEffect, useMemo, useState } from "react";
import { Link } from "react-router-dom";
import api from "../api/axios";
import Navbar from "../components/Navbar";
import { getApiErrorMessage } from "../utils/apiError";
import { formatPrice, formatRating, getPlaceSubtitle, isFoodCategory } from "../utils/format";
import { getPlaceExternalLink } from "../utils/externalLinks";
import { applyImageFallback } from "../utils/images";
import "./Explore.css";

function Food() {
  const [places, setPlaces] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchPlaces = async () => {
      try {
        const { data } = await api.get("/api/places");
        setPlaces(data);
      } catch (err) {
        setError(getApiErrorMessage(err, "Failed to load food places."));
      } finally {
        setLoading(false);
      }
    };

    fetchPlaces();
  }, []);

  const foodPlaces = useMemo(() => places.filter((place) => isFoodCategory(place.category)), [places]);

  if (loading) {
    return (
      <div className="explore-page">
        <Navbar />
        <div className="explore-shell">
          <div className="explore-feedback">Loading food places...</div>
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
        <section className="explore-hero explore-hero--food">
          <span className="explore-hero__label">Food in Almaty</span>
          <h1>Find trusted restaurants, cafes, and tourist-friendly places to eat.</h1>
          <p>
            This page uses backend place data and filters it into a food-focused section for
            visitors who want somewhere reliable after a tour or city walk.
          </p>

          <div className="explore-stats">
            <div>
              <strong>{foodPlaces.length}</strong>
              <span>Food locations</span>
            </div>
            <div>
              <strong>{foodPlaces.filter((place) => place.isVerified).length}</strong>
              <span>Verified picks</span>
            </div>
            <div>
              <strong>{foodPlaces.filter((place) => place.isFeatured).length}</strong>
              <span>Featured picks</span>
            </div>
          </div>
        </section>

        <div className="explore-toolbar">
          <div>
            <h2>Food Cards</h2>
            <p>Open a place card to see full details, pricing, category, and location info.</p>
          </div>
        </div>

        <div className="explore-grid">
          {foodPlaces.length ? (
            foodPlaces.map((place) => (
              <article className="explore-card" key={place.id}>
                <img
                  src={place.imageUrl || "https://via.placeholder.com/400x250?text=Food+Place"}
                  alt={place.title}
                  onError={(event) =>
                    applyImageFallback(
                      event,
                      "https://images.unsplash.com/photo-1414235077428-338989a2e8c0?auto=format&fit=crop&w=1200&q=80"
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
                    <span className="explore-chip">{formatPrice(place.averagePrice)}</span>
                    <span className="explore-chip">Rating {formatRating(place.rating)}</span>
                    <span className="explore-chip">{place.isVerified ? "Verified" : "Open data"}</span>
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
            <div className="explore-empty">No food places found.</div>
          )}
        </div>
      </div>
    </main>
  );
}

export default Food;
