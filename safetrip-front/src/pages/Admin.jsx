import { useEffect, useMemo, useState } from "react";
import Navbar from "../components/Navbar";
import api from "../api/axios";
import { getApiErrorMessage } from "../utils/apiError";
import { formatPrice, formatRating, getPlaceSubtitle, isFoodCategory } from "../utils/format";
import { getCurrentUser, isAdminUser, isAuthenticated } from "../utils/auth";
import { applyImageFallback } from "../utils/images";
import "./Explore.css";

const initialTourForm = {
  title: "",
  description: "",
  city: "Almaty",
  startDate: "",
  startTime: "",
  capacity: 20,
  durationDays: 1,
  price: "",
  rating: "",
  imageUrl: "",
  isFeatured: false,
  isVerified: true,
  startLat: "",
  startLng: "",
  instagramUrl: "",
};

const initialPlaceForm = {
  title: "",
  description: "",
  category: "Restaurant",
  averagePrice: "",
  rating: "",
  imageUrl: "",
  isFeatured: false,
  isVerified: true,
  city: "Almaty",
  latitude: "",
  longitude: "",
  twoGisUrl: "",
};

function normalizeTourForm(data) {
  return {
    title: data.title || "",
    description: data.description || "",
    city: data.city || "Almaty",
    startDate: data.startDate || "",
    startTime: data.startTime ? String(data.startTime).slice(0, 5) : "",
    capacity: data.capacity ?? 20,
    durationDays: data.durationDays ?? 1,
    price: data.price ?? "",
    rating: data.rating ?? "",
    imageUrl: data.imageUrl || "",
    isFeatured: Boolean(data.isFeatured),
    isVerified: data.isVerified ?? true,
    startLat: data.startLat ?? "",
    startLng: data.startLng ?? "",
    instagramUrl: data.instagramUrl || "",
  };
}

function normalizePlaceForm(data) {
  return {
    title: data.title || "",
    description: data.description || "",
    category: data.category || "Restaurant",
    averagePrice: data.averagePrice ?? "",
    rating: data.rating ?? "",
    imageUrl: data.imageUrl || "",
    isFeatured: Boolean(data.isFeatured),
    isVerified: data.isVerified ?? true,
    city: data.city || "Almaty",
    latitude: data.latitude ?? "",
    longitude: data.longitude ?? "",
    twoGisUrl: data.twoGisUrl || "",
  };
}

function validateCoordinate(value, label, min, max, required = false) {
  if (value === "" || value === null || value === undefined) {
    return required ? `${label} is required.` : null;
  }

  const numericValue = Number(value);

  if (Number.isNaN(numericValue)) {
    return `${label} must be a valid number.`;
  }

  if (numericValue < min || numericValue > max) {
    return `${label} must be between ${min} and ${max}.`;
  }

  return null;
}

function Admin() {
  const user = getCurrentUser();
  const [tourForm, setTourForm] = useState(initialTourForm);
  const [placeForm, setPlaceForm] = useState(initialPlaceForm);
  const [tourMessage, setTourMessage] = useState("");
  const [placeMessage, setPlaceMessage] = useState("");
  const [tourLoading, setTourLoading] = useState(false);
  const [placeLoading, setPlaceLoading] = useState(false);
  const [tours, setTours] = useState([]);
  const [places, setPlaces] = useState([]);
  const [loadingData, setLoadingData] = useState(true);
  const [tourEditingId, setTourEditingId] = useState(null);
  const [placeEditingId, setPlaceEditingId] = useState(null);
  const [bookings, setBookings] = useState([]);

  const foodPlaces = useMemo(() => places.filter((place) => isFoodCategory(place.category)), [places]);
  const entertainmentPlaces = useMemo(
    () => places.filter((place) => !isFoodCategory(place.category)),
    [places]
  );

  useEffect(() => {
    const loadData = async () => {
      if (!isAuthenticated() || !isAdminUser()) {
        return;
      }

      setLoadingData(true);
      try {
        const [{ data: toursData }, { data: placesData }, { data: bookingsData }] = await Promise.all([
          api.get("/api/tours"),
          api.get("/api/places"),
          api.get("/api/tour-bookings"),
        ]);

        setTours(toursData);
        setPlaces(placesData);
        setBookings(bookingsData);
      } catch (err) {
        setTourMessage(getApiErrorMessage(err, "Failed to load admin data."));
      } finally {
        setLoadingData(false);
      }
    };

    loadData();
  }, []);

  if (!isAuthenticated()) {
    return (
      <div className="explore-page">
        <Navbar />
        <div className="explore-shell">
          <div className="explore-feedback">Login first to open the admin panel.</div>
        </div>
      </div>
    );
  }

  if (!isAdminUser()) {
    return (
      <div className="explore-page">
        <Navbar />
        <div className="explore-shell">
          <div className="explore-feedback">
            This page is intended for admin or moderator accounts. Current role: {user?.role}
          </div>
        </div>
      </div>
    );
  }

  const refreshData = async () => {
    const [{ data: toursData }, { data: placesData }, { data: bookingsData }] = await Promise.all([
      api.get("/api/tours"),
      api.get("/api/places"),
      api.get("/api/tour-bookings"),
    ]);
    setTours(toursData);
    setPlaces(placesData);
    setBookings(bookingsData);
  };

  const handleTourChange = (event) => {
    const { name, value, type, checked } = event.target;
    setTourForm((current) => ({
      ...current,
      [name]: type === "checkbox" ? checked : value,
    }));
  };

  const handlePlaceChange = (event) => {
    const { name, value, type, checked } = event.target;
    setPlaceForm((current) => ({
      ...current,
      [name]: type === "checkbox" ? checked : value,
    }));
  };

  const resetTourForm = () => {
    setTourForm(initialTourForm);
    setTourEditingId(null);
    setTourMessage("");
  };

  const resetPlaceForm = () => {
    setPlaceForm(initialPlaceForm);
    setPlaceEditingId(null);
    setPlaceMessage("");
  };

  const submitTour = async (event) => {
    event.preventDefault();
    setTourLoading(true);
    setTourMessage("");

    try {
      const latitudeError = validateCoordinate(tourForm.startLat, "Start latitude", -90, 90);
      const longitudeError = validateCoordinate(tourForm.startLng, "Start longitude", -180, 180);

      if (latitudeError || longitudeError) {
        setTourMessage(latitudeError || longitudeError);
        return;
      }

      const payload = {
        ...tourForm,
        capacity: Number(tourForm.capacity),
        durationDays: Number(tourForm.durationDays),
        price: Number(tourForm.price),
        rating: tourForm.rating ? Number(tourForm.rating) : null,
        startLat: tourForm.startLat ? Number(tourForm.startLat) : null,
        startLng: tourForm.startLng ? Number(tourForm.startLng) : null,
      };

      if (tourEditingId) {
        await api.put(`/api/tours/${tourEditingId}`, payload);
        setTourMessage("Tour updated successfully.");
      } else {
        await api.post("/api/tours", payload);
        setTourMessage("Tour created successfully.");
      }

      resetTourForm();
      await refreshData();
    } catch (err) {
      setTourMessage(
        getApiErrorMessage(err, tourEditingId ? "Failed to update tour." : "Failed to create tour.")
      );
    } finally {
      setTourLoading(false);
    }
  };

  const submitPlace = async (event) => {
    event.preventDefault();
    setPlaceLoading(true);
    setPlaceMessage("");

    try {
      const latitudeError = validateCoordinate(placeForm.latitude, "Latitude", -90, 90, true);
      const longitudeError = validateCoordinate(placeForm.longitude, "Longitude", -180, 180, true);

      if (latitudeError || longitudeError) {
        setPlaceMessage(latitudeError || longitudeError);
        return;
      }

      const payload = {
        ...placeForm,
        averagePrice: placeForm.averagePrice ? Number(placeForm.averagePrice) : null,
        rating: placeForm.rating ? Number(placeForm.rating) : null,
        latitude: Number(placeForm.latitude),
        longitude: Number(placeForm.longitude),
      };

      if (placeEditingId) {
        await api.put(`/api/places/${placeEditingId}`, payload);
        setPlaceMessage("Place updated successfully.");
      } else {
        await api.post("/api/places", payload);
        setPlaceMessage("Place created successfully.");
      }

      resetPlaceForm();
      await refreshData();
    } catch (err) {
      setPlaceMessage(
        getApiErrorMessage(
          err,
          placeEditingId ? "Failed to update place." : "Failed to create place."
        )
      );
    } finally {
      setPlaceLoading(false);
    }
  };

  const startEditTour = (tour) => {
    setTourEditingId(tour.id);
    setTourForm(normalizeTourForm(tour));
    setTourMessage(`Editing "${tour.title}"`);
    window.scrollTo({ top: 0, behavior: "smooth" });
  };

  const startEditPlace = (place) => {
    setPlaceEditingId(place.id);
    setPlaceForm(normalizePlaceForm(place));
    setPlaceMessage(`Editing "${place.title}"`);
    window.scrollTo({ top: 0, behavior: "smooth" });
  };

  const deleteTour = async (tour) => {
    if (!window.confirm(`Delete tour "${tour.title}"?`)) {
      return;
    }

    try {
      await api.delete(`/api/tours/${tour.id}`);
      if (tourEditingId === tour.id) {
        resetTourForm();
      }
      setTourMessage("Tour deleted successfully.");
      await refreshData();
    } catch (err) {
      setTourMessage(getApiErrorMessage(err, "Failed to delete tour."));
    }
  };

  const deletePlace = async (place) => {
    if (!window.confirm(`Delete place "${place.title}"?`)) {
      return;
    }

    try {
      await api.delete(`/api/places/${place.id}`);
      if (placeEditingId === place.id) {
        resetPlaceForm();
      }
      setPlaceMessage("Place deleted successfully.");
      await refreshData();
    } catch (err) {
      setPlaceMessage(getApiErrorMessage(err, "Failed to delete place."));
    }
  };

  return (
    <main className="explore-page">
      <Navbar />

      <div className="explore-shell">
        <section className="explore-hero explore-hero--admin">
          <span className="explore-hero__label">Admin</span>
          <h1>Publish, edit, and delete tours, restaurants, and entertainment content.</h1>
          <p>
            In this page admin
            can manage site content.
          </p>

          <div className="explore-stats">
            <div>
              <strong>{user?.role}</strong>
              <span>Current role</span>
            </div>
            <div>
              <strong>{tours.length}</strong>
              <span>Total tours</span>
            </div>
            <div>
              <strong>{places.length}</strong>
              <span>Total places</span>
            </div>
            <div>
              <strong>{bookings.length}</strong>
              <span>Total bookings</span>
            </div>
          </div>
        </section>

        <div className="admin-grid">
          <section className="admin-card">
            <div className="admin-card__header">
              <h3>{tourEditingId ? "Edit Tour" : "Create Tour"}</h3>
              {tourEditingId ? (
                <button type="button" className="explore-button" onClick={resetTourForm}>
                  Cancel edit
                </button>
              ) : null}
            </div>
            <form className="admin-form" onSubmit={submitTour}>
              <input name="title" placeholder="Tour title" value={tourForm.title} onChange={handleTourChange} required />
              <textarea name="description" placeholder="Description" value={tourForm.description} onChange={handleTourChange} rows="4" />
              <input name="city" placeholder="City" value={tourForm.city} onChange={handleTourChange} required />
              <input name="startDate" type="date" value={tourForm.startDate} onChange={handleTourChange} required />
              <input name="startTime" type="time" value={tourForm.startTime} onChange={handleTourChange} required />
              <input name="capacity" type="number" min="1" placeholder="Available seats" value={tourForm.capacity} onChange={handleTourChange} required />
              <input name="durationDays" type="number" placeholder="Duration days" value={tourForm.durationDays} onChange={handleTourChange} required />
              <input name="price" type="number" step="0.01" placeholder="Price" value={tourForm.price} onChange={handleTourChange} required />
              <input name="rating" type="number" step="0.1" placeholder="Rating" value={tourForm.rating} onChange={handleTourChange} />
              <input name="imageUrl" placeholder="Image URL" value={tourForm.imageUrl} onChange={handleTourChange} />
              <input name="instagramUrl" placeholder="Instagram link" value={tourForm.instagramUrl} onChange={handleTourChange} />
              <input name="startLat" type="number" step="0.000001" min="-90" max="90" placeholder="Start latitude (-90 to 90)" value={tourForm.startLat} onChange={handleTourChange} />
              <input name="startLng" type="number" step="0.000001" min="-180" max="180" placeholder="Start longitude (-180 to 180)" value={tourForm.startLng} onChange={handleTourChange} />
              <label className="admin-check"><input name="isFeatured" type="checkbox" checked={tourForm.isFeatured} onChange={handleTourChange} /> Featured</label>
              <label className="admin-check"><input name="isVerified" type="checkbox" checked={tourForm.isVerified} onChange={handleTourChange} /> Verified</label>
              <button type="submit" disabled={tourLoading}>
                {tourLoading ? "Saving..." : tourEditingId ? "Update Tour" : "Create Tour"}
              </button>
            </form>
            {tourMessage ? <p className="explore-note">{tourMessage}</p> : null}
          </section>

          <section className="admin-card">
            <div className="admin-card__header">
              <h3>{placeEditingId ? "Edit Place" : "Create Place"}</h3>
              {placeEditingId ? (
                <button type="button" className="explore-button" onClick={resetPlaceForm}>
                  Cancel edit
                </button>
              ) : null}
            </div>
            <form className="admin-form" onSubmit={submitPlace}>
              <input name="title" placeholder="Place title" value={placeForm.title} onChange={handlePlaceChange} required />
              <textarea name="description" placeholder="Description" value={placeForm.description} onChange={handlePlaceChange} rows="4" />
              <select name="category" value={placeForm.category} onChange={handlePlaceChange}>
                <option value="Restaurant">Restaurant</option>
                <option value="Cafe">Cafe</option>
                <option value="Viewpoint">Viewpoint</option>
                <option value="Museum">Museum</option>
                <option value="Entertainment">Entertainment</option>
              </select>
              <input name="averagePrice" type="number" step="0.01" placeholder="Average price" value={placeForm.averagePrice} onChange={handlePlaceChange} />
              <input name="rating" type="number" step="0.1" placeholder="Rating" value={placeForm.rating} onChange={handlePlaceChange} />
              <input name="imageUrl" placeholder="Image URL" value={placeForm.imageUrl} onChange={handlePlaceChange} />
              <input name="twoGisUrl" placeholder="2GIS link" value={placeForm.twoGisUrl} onChange={handlePlaceChange} />
              <input name="city" placeholder="City" value={placeForm.city} onChange={handlePlaceChange} required />
              <input name="latitude" type="number" step="0.000001" min="-90" max="90" placeholder="Latitude (-90 to 90)" value={placeForm.latitude} onChange={handlePlaceChange} required />
              <input name="longitude" type="number" step="0.000001" min="-180" max="180" placeholder="Longitude (-180 to 180)" value={placeForm.longitude} onChange={handlePlaceChange} required />
              <label className="admin-check"><input name="isFeatured" type="checkbox" checked={placeForm.isFeatured} onChange={handlePlaceChange} /> Featured</label>
              <label className="admin-check"><input name="isVerified" type="checkbox" checked={placeForm.isVerified} onChange={handlePlaceChange} /> Verified</label>
              <button type="submit" disabled={placeLoading}>
                {placeLoading ? "Saving..." : placeEditingId ? "Update Place" : "Create Place"}
              </button>
            </form>
            {placeMessage ? <p className="explore-note">{placeMessage}</p> : null}
          </section>
        </div>

        <div className="explore-toolbar admin-section-toolbar">
          <div>
            <h2>Tour Bookings</h2>
            <p>Users who booked a tour and the ticket details they submitted.</p>
          </div>
        </div>

        <div className="explore-grid">
          {bookings.length ? (
            bookings.map((booking) => (
              <article className="security-card ticket-card" key={booking.id}>
                <p className="details-main__eyebrow">{booking.tourTitle}</p>
                <h3>{booking.fullName}</h3>
                <p>User account: {booking.userEmail}</p>
                <p>Contact email: {booking.contactEmail}</p>
                <p>Phone: {booking.phoneNumber}</p>
                <p>Seat count: 1</p>
                <p>Ticket code: {booking.ticketCode}</p>
                <p>Status: {booking.status}</p>
                <p>Created at: {new Date(booking.createdAt).toLocaleString()}</p>
                {booking.notes ? <p>Notes: {booking.notes}</p> : null}
              </article>
            ))
          ) : (
            <div className="explore-empty">No tour bookings yet.</div>
          )}
        </div>

        <div className="explore-toolbar admin-section-toolbar">
          <div>
            <h2>Manage Tours</h2>
            <p>Edit existing tour cards or remove them from the project.</p>
          </div>
        </div>

        {loadingData ? (
          <div className="explore-feedback">Loading admin content...</div>
        ) : (
          <div className="explore-grid">
            {tours.length ? (
              tours.map((tour) => (
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
                      <span className="explore-chip">{tour.startDate || "Date TBA"}</span>
                      <span className="explore-chip">{tour.startTime ? String(tour.startTime).slice(0, 5) : "Time TBA"}</span>
                      <span className="explore-chip">{tour.remainingSeats ?? tour.capacity ?? 0} seats left</span>
                    </div>
                    <div className="explore-card__actions">
                      <button type="button" className="explore-button" onClick={() => startEditTour(tour)}>
                        Edit
                      </button>
                      <button type="button" className="explore-button explore-button--danger" onClick={() => deleteTour(tour)}>
                        Delete
                      </button>
                    </div>
                  </div>
                </article>
              ))
            ) : (
              <div className="explore-empty">No tours found.</div>
            )}
          </div>
        )}

        <div className="explore-toolbar admin-section-toolbar">
          <div>
            <h2>Manage Food</h2>
            <p>Change restaurants and cafes or remove them from the catalog.</p>
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
                  <p className="explore-card__meta">{getPlaceSubtitle(place)}</p>
                  <h3>{place.title}</h3>
                  <p className="explore-card__text">{place.description || "No description available."}</p>
                  <div className="explore-card__info">
                    <span className="explore-chip">{formatPrice(place.averagePrice)}</span>
                    <span className="explore-chip">Rating {formatRating(place.rating)}</span>
                  </div>
                  <div className="explore-card__actions">
                    <button type="button" className="explore-button" onClick={() => startEditPlace(place)}>
                      Edit
                    </button>
                    <button type="button" className="explore-button explore-button--danger" onClick={() => deletePlace(place)}>
                      Delete
                    </button>
                  </div>
                </div>
              </article>
            ))
          ) : (
            <div className="explore-empty">No food places found.</div>
          )}
        </div>

        <div className="explore-toolbar admin-section-toolbar">
          <div>
            <h2>Manage Entertainment</h2>
            <p>Update viewpoints, museums, and other city experiences.</p>
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
                  <p className="explore-card__meta">{getPlaceSubtitle(place)}</p>
                  <h3>{place.title}</h3>
                  <p className="explore-card__text">{place.description || "No description available."}</p>
                  <div className="explore-card__info">
                    <span className="explore-chip">{place.category}</span>
                    <span className="explore-chip">{formatPrice(place.averagePrice)}</span>
                  </div>
                  <div className="explore-card__actions">
                    <button type="button" className="explore-button" onClick={() => startEditPlace(place)}>
                      Edit
                    </button>
                    <button type="button" className="explore-button explore-button--danger" onClick={() => deletePlace(place)}>
                      Delete
                    </button>
                  </div>
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

export default Admin;
