import { useEffect, useMemo, useState } from "react";
import { Link } from "react-router-dom";
import api from "../api/axios";
import Navbar from "../components/Navbar";
import { getApiErrorMessage } from "../utils/apiError";
import { formatPrice, formatRating, getPlaceSubtitle, isFoodCategory } from "../utils/format";
import { getCurrentUser, isAuthenticated } from "../utils/auth";
import { applyImageFallback } from "../utils/images";
import "./Explore.css";

function Profile() {
  const user = getCurrentUser();
  const [favorites, setFavorites] = useState(null);
  const [tours, setTours] = useState([]);
  const [places, setPlaces] = useState([]);
  const [bookings, setBookings] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [bookingMessage, setBookingMessage] = useState("");
  const [cancelingBookingId, setCancelingBookingId] = useState(null);

  useEffect(() => {
    const fetchProfileData = async () => {
      if (!isAuthenticated()) {
        setError("Login first to view your profile and favorites.");
        setLoading(false);
        return;
      }

      try {
        const [{ data: favoritesData }, { data: toursData }, { data: placesData }, { data: bookingsData }] = await Promise.all([
          api.get("/api/profile/favorites"),
          api.get("/api/tours"),
          api.get("/api/places"),
          api.get("/api/tour-bookings/my"),
        ]);

        setFavorites(favoritesData);
        setTours(toursData);
        setPlaces(placesData);
        setBookings(bookingsData);
      } catch (err) {
        setError(getApiErrorMessage(err, "Failed to load profile data."));
      } finally {
        setLoading(false);
      }
    };

    fetchProfileData();
  }, []);

  const favoriteTours = useMemo(() => {
    if (!favorites) {
      return [];
    }

    return favorites.tours
      .map((favorite) => tours.find((tour) => tour.id === favorite.tourId))
      .filter(Boolean);
  }, [favorites, tours]);

  const favoritePlaces = useMemo(() => {
    if (!favorites) {
      return [];
    }

    return favorites.places
      .map((favorite) => places.find((place) => place.id === favorite.placeId))
      .filter(Boolean);
  }, [favorites, places]);

  const cancelBooking = async (bookingId) => {
    if (!window.confirm("Cancel this booking?")) {
      return;
    }

    setCancelingBookingId(bookingId);
    setBookingMessage("");

    try {
      await api.delete(`/api/tour-bookings/${bookingId}`);
      setBookings((current) => current.filter((booking) => booking.id !== bookingId));
      setBookingMessage("Booking canceled successfully.");
    } catch (err) {
      setBookingMessage(getApiErrorMessage(err, "Failed to cancel booking."));
    } finally {
      setCancelingBookingId(null);
    }
  };

  if (loading) {
    return (
      <div className="explore-page">
        <Navbar />
        <div className="explore-shell">
          <div className="explore-feedback">Loading profile...</div>
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
        <section className="explore-hero explore-hero--profile">
          <span className="explore-hero__label">Profile</span>
          <h1>Your saved places and routes in one page.</h1>
          <p>
            This page shows the current user, favorite tours and favorite places.
          </p>

          <div className="explore-stats">
            <div>
              <strong>{user?.role || "USER"}</strong>
              <span>Current role</span>
            </div>
            <div>
              <strong>{favoriteTours.length}</strong>
              <span>Favorite tours</span>
            </div>
            <div>
              <strong>{favoritePlaces.length}</strong>
              <span>Favorite places</span>
            </div>
            <div>
              <strong>{bookings.length}</strong>
              <span>Tickets</span>
            </div>
          </div>
        </section>

        <div className="profile-summary">
          <div className="security-card">
            <h3>Account</h3>
            <p>Email: {user?.email}</p>
            <p>Role: {user?.role}</p>
          </div>
        </div>

        <div className="explore-toolbar">
          <div>
            <h2>My Tickets</h2>
            <p>Each booking keeps a simple ticket code that you can show later.</p>
          </div>
        </div>

        {bookingMessage ? <p className="explore-note">{bookingMessage}</p> : null}

        <div className="explore-grid">
          {bookings.length ? (
            bookings.map((booking) => (
              <article className="security-card ticket-card" key={booking.id}>
                <p className="details-main__eyebrow">Tour booking</p>
                <h3>{booking.tourTitle}</h3>
                <p>Ticket code: {booking.ticketCode}</p>
                <p>Full name: {booking.fullName}</p>
                <p>Phone: {booking.phoneNumber}</p>
                <p>Seat count: 1</p>
                <p>Status: {booking.status}</p>
                <p>Booked at: {new Date(booking.createdAt).toLocaleString()}</p>
                <div className="explore-card__actions">
                  <Link to={`/tours/${booking.tourId}`} className="explore-link">
                    Open Tour
                  </Link>
                  <button
                    type="button"
                    className="explore-button explore-button--danger"
                    onClick={() => cancelBooking(booking.id)}
                    disabled={cancelingBookingId === booking.id}
                  >
                    {cancelingBookingId === booking.id ? "Canceling..." : "Cancel Booking"}
                  </button>
                </div>
              </article>
            ))
          ) : (
            <div className="explore-empty">No tickets yet. Book a tour to see your ticket here.</div>
          )}
        </div>

        <div className="explore-toolbar">
          <div>
            <h2>Favorite Tours</h2>
            <p>Saved routes that the user marked from the tour detail page.</p>
          </div>
        </div>

        <div className="explore-grid">
          {favoriteTours.length ? (
            favoriteTours.map((tour) => (
              <article className="explore-card" key={tour.id}>
                <img
                  src={tour.imageUrl || "https://via.placeholder.com/400x250?text=Tour"}
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
                  <p className="explore-card__text">{tour.description || "No description available."}</p>
                  <div className="explore-card__info">
                    <span className="explore-chip">{formatPrice(tour.price)}</span>
                    <span className="explore-chip">Rating {formatRating(tour.rating)}</span>
                  </div>
                  <div className="explore-card__actions">
                    <Link to={`/tours/${tour.id}`} className="explore-link">
                      Open Tour
                    </Link>
                  </div>
                </div>
              </article>
            ))
          ) : (
            <div className="explore-empty">No favorite tours yet.</div>
          )}
        </div>

        <div className="explore-toolbar profile-toolbar">
          <div>
            <h2>Favorite Places</h2>
            <p>Saved places from food and entertainment detail pages.</p>
          </div>
        </div>

        <div className="explore-grid">
          {favoritePlaces.length ? (
            favoritePlaces.map((place) => (
              <article className="explore-card" key={place.id}>
                <img
                  src={place.imageUrl || "https://via.placeholder.com/400x250?text=Place"}
                  alt={place.title}
                  onError={(event) =>
                    applyImageFallback(
                      event,
                      isFoodCategory(place.category)
                        ? "https://images.unsplash.com/photo-1414235077428-338989a2e8c0?auto=format&fit=crop&w=1200&q=80"
                        : "https://images.unsplash.com/photo-1512453979798-5ea266f8880c?auto=format&fit=crop&w=1200&q=80"
                    )
                  }
                />
                <div className="explore-card__body">
                  <p className="explore-card__meta">{getPlaceSubtitle(place)}</p>
                  <h3>{place.title}</h3>
                  <p className="explore-card__text">{place.description || "No description available."}</p>
                  <div className="explore-card__info">
                    <span className="explore-chip">{place.category}</span>
                    <span className="explore-chip">{formatPrice(place.averagePrice)}</span>
                  </div>
                  <div className="explore-card__actions">
                    <Link to={`/places/${place.id}`} className="explore-link">
                      Open Place
                    </Link>
                  </div>
                </div>
              </article>
            ))
          ) : (
            <div className="explore-empty">No favorite places yet.</div>
          )}
        </div>
      </div>
    </main>
  );
}

export default Profile;
