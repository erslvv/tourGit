import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import api from "../api/axios";
import Navbar from "../components/Navbar";
import { getApiErrorMessage } from "../utils/apiError";
import { formatPrice, formatRating } from "../utils/format";
import { getCurrentUser, isAuthenticated } from "../utils/auth";
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
  const [bookingOpen, setBookingOpen] = useState(false);
  const [bookingLoading, setBookingLoading] = useState(false);
  const [bookingMessage, setBookingMessage] = useState("");
  const [hasBooked, setHasBooked] = useState(false);
  const user = getCurrentUser();
  const [bookingForm, setBookingForm] = useState({
    fullName: "",
    phoneNumber: "",
    contactEmail: user?.email || "",
    notes: "",
  });
  const instagramLink = tour?.instagramUrl || getTourExternalLink(tour?.title);

  useEffect(() => {
    const fetchTour = async () => {
      try {
        const [{ data: tourData }, favoritesResponse, bookingsResponse] = await Promise.all([
          api.get(`/api/tours/${id}`),
          isAuthenticated() ? api.get("/api/profile/favorites") : Promise.resolve({ data: null }),
          isAuthenticated() ? api.get("/api/tour-bookings/my") : Promise.resolve({ data: [] }),
        ]);

        setTour(tourData);

        if (favoritesResponse.data) {
          const favorite = favoritesResponse.data.tours.find((item) => item.tourId === Number(id));
          setIsFavorite(Boolean(favorite));
        }

        setHasBooked(bookingsResponse.data.some((item) => item.tourId === Number(id)));
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

  const handleBookingChange = (event) => {
    const { name, value } = event.target;
    setBookingForm((current) => ({
      ...current,
      [name]: value,
    }));
  };

  const submitBooking = async (event) => {
    event.preventDefault();

    if (!isAuthenticated()) {
      setBookingMessage("Login first to book this tour.");
      return;
    }

    setBookingLoading(true);
    setBookingMessage("");

    try {
      const { data } = await api.post("/api/tour-bookings", {
        tourId: Number(id),
        fullName: bookingForm.fullName,
        phoneNumber: bookingForm.phoneNumber,
        contactEmail: bookingForm.contactEmail,
        notes: bookingForm.notes,
      });

      setBookingMessage(`Booking confirmed. Ticket code: ${data.ticketCode}`);
      setBookingOpen(false);
      setHasBooked(true);
      setBookingForm((current) => ({
        ...current,
        notes: "",
      }));
    } catch (err) {
      setBookingMessage(getApiErrorMessage(err, "Booking failed. Please try again."));
    } finally {
      setBookingLoading(false);
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
                onClick={() => {
                  if (!isAuthenticated()) {
                    setBookingMessage("Login first to book this tour.");
                    return;
                  }
                  if (hasBooked) {
                    setBookingMessage("You already booked this tour.");
                    return;
                  }
                  if (tour?.remainingSeats !== null && tour?.remainingSeats !== undefined && tour.remainingSeats < 1) {
                    setBookingMessage("No seats left for this tour.");
                    return;
                  }
                  setBookingOpen((current) => !current);
                  setBookingMessage("");
                }}
                disabled={hasBooked || (tour?.remainingSeats !== null && tour?.remainingSeats !== undefined && tour.remainingSeats < 1)}
              >
                {hasBooked
                  ? "Already booked"
                  : tour?.remainingSeats !== null && tour?.remainingSeats !== undefined && tour.remainingSeats < 1
                    ? "No seats left"
                    : bookingOpen
                      ? "Close booking form"
                      : "Book this tour"}
              </button>

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

            {bookingOpen ? (
              <form className="booking-form" onSubmit={submitBooking}>
                <h2>Book this tour</h2>
                <p>Send your details to the admin and keep the ticket in your profile.</p>
                <input
                  name="fullName"
                  placeholder="Full name"
                  value={bookingForm.fullName}
                  onChange={handleBookingChange}
                  required
                />
                <input
                  name="phoneNumber"
                  placeholder="Phone number"
                  value={bookingForm.phoneNumber}
                  onChange={handleBookingChange}
                  required
                />
                <input
                  name="contactEmail"
                  type="email"
                  placeholder="Contact email"
                  value={bookingForm.contactEmail}
                  onChange={handleBookingChange}
                  required
                />
                <textarea
                  name="notes"
                  placeholder="Extra notes for the admin"
                  value={bookingForm.notes}
                  onChange={handleBookingChange}
                  rows="4"
                />
                <button className="explore-link booking-form__submit" type="submit" disabled={bookingLoading}>
                  {bookingLoading ? "Sending..." : "Confirm booking"}
                </button>
              </form>
            ) : null}

            {favoriteMessage ? <p className="explore-note">{favoriteMessage}</p> : null}
            {bookingMessage ? <p className="explore-note">{bookingMessage}</p> : null}
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
                <dt>Date</dt>
                <dd>{tour.startDate || "TBA"}</dd>
              </div>
              <div>
                <dt>Time</dt>
                <dd>{tour.startTime ? String(tour.startTime).slice(0, 5) : "TBA"}</dd>
              </div>
              <div>
                <dt>Seats left</dt>
                <dd>{tour.remainingSeats ?? tour.capacity ?? "TBA"}</dd>
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
