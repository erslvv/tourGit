import { useState } from "react";
import Navbar from "../components/Navbar";
import api from "../api/axios";
import { getApiErrorMessage } from "../utils/apiError";
import { getCurrentUser, isAdminUser, isAuthenticated } from "../utils/auth";
import "./Explore.css";

const initialTourForm = {
  title: "",
  description: "",
  city: "Almaty",
  durationDays: 1,
  price: "",
  rating: "",
  imageUrl: "",
  isFeatured: false,
  isVerified: true,
  startLat: "",
  startLng: "",
  h3Index: "",
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
  h3Index: "",
};

function Admin() {
  const user = getCurrentUser();
  const [tourForm, setTourForm] = useState(initialTourForm);
  const [placeForm, setPlaceForm] = useState(initialPlaceForm);
  const [tourMessage, setTourMessage] = useState("");
  const [placeMessage, setPlaceMessage] = useState("");
  const [tourLoading, setTourLoading] = useState(false);
  const [placeLoading, setPlaceLoading] = useState(false);

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

  const submitTour = async (event) => {
    event.preventDefault();
    setTourLoading(true);
    setTourMessage("");

    try {
      await api.post("/api/tours", {
        ...tourForm,
        durationDays: Number(tourForm.durationDays),
        price: Number(tourForm.price),
        rating: tourForm.rating ? Number(tourForm.rating) : null,
        startLat: tourForm.startLat ? Number(tourForm.startLat) : null,
        startLng: tourForm.startLng ? Number(tourForm.startLng) : null,
      });
      setTourMessage("Tour created successfully.");
      setTourForm(initialTourForm);
    } catch (err) {
      setTourMessage(getApiErrorMessage(err, "Failed to create tour."));
    } finally {
      setTourLoading(false);
    }
  };

  const submitPlace = async (event) => {
    event.preventDefault();
    setPlaceLoading(true);
    setPlaceMessage("");

    try {
      await api.post("/api/places", {
        ...placeForm,
        averagePrice: placeForm.averagePrice ? Number(placeForm.averagePrice) : null,
        rating: placeForm.rating ? Number(placeForm.rating) : null,
        latitude: Number(placeForm.latitude),
        longitude: Number(placeForm.longitude),
      });
      setPlaceMessage("Place created successfully.");
      setPlaceForm(initialPlaceForm);
    } catch (err) {
      setPlaceMessage(getApiErrorMessage(err, "Failed to create place."));
    } finally {
      setPlaceLoading(false);
    }
  };

  return (
    <main className="explore-page">
      <Navbar />

      <div className="explore-shell">
        <section className="explore-hero">
          <span className="explore-hero__label">Admin</span>
          <h1>Publish tours, restaurants, and entertainment content.</h1>
          <p>
            This panel uses the existing backend create endpoints so an admin can add new tours and
            places directly from the frontend.
          </p>

          <div className="explore-stats">
            <div>
              <strong>{user?.role}</strong>
              <span>Current role</span>
            </div>
            <div>
              <strong>/api/tours</strong>
              <span>Create tours</span>
            </div>
            <div>
              <strong>/api/places</strong>
              <span>Create food and entertainment</span>
            </div>
          </div>
        </section>

        <div className="admin-grid">
          <section className="admin-card">
            <h3>Create Tour</h3>
            <form className="admin-form" onSubmit={submitTour}>
              <input name="title" placeholder="Tour title" value={tourForm.title} onChange={handleTourChange} required />
              <textarea name="description" placeholder="Description" value={tourForm.description} onChange={handleTourChange} rows="4" />
              <input name="city" placeholder="City" value={tourForm.city} onChange={handleTourChange} required />
              <input name="durationDays" type="number" placeholder="Duration days" value={tourForm.durationDays} onChange={handleTourChange} required />
              <input name="price" type="number" step="0.01" placeholder="Price" value={tourForm.price} onChange={handleTourChange} required />
              <input name="rating" type="number" step="0.1" placeholder="Rating" value={tourForm.rating} onChange={handleTourChange} />
              <input name="imageUrl" placeholder="Image URL" value={tourForm.imageUrl} onChange={handleTourChange} />
              <input name="startLat" type="number" step="0.000001" placeholder="Start latitude" value={tourForm.startLat} onChange={handleTourChange} />
              <input name="startLng" type="number" step="0.000001" placeholder="Start longitude" value={tourForm.startLng} onChange={handleTourChange} />
              <input name="h3Index" placeholder="H3 index" value={tourForm.h3Index} onChange={handleTourChange} />
              <label className="admin-check"><input name="isFeatured" type="checkbox" checked={tourForm.isFeatured} onChange={handleTourChange} /> Featured</label>
              <label className="admin-check"><input name="isVerified" type="checkbox" checked={tourForm.isVerified} onChange={handleTourChange} /> Verified</label>
              <button type="submit" disabled={tourLoading}>{tourLoading ? "Creating..." : "Create Tour"}</button>
            </form>
            {tourMessage ? <p className="explore-note">{tourMessage}</p> : null}
          </section>

          <section className="admin-card">
            <h3>Create Place</h3>
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
              <input name="city" placeholder="City" value={placeForm.city} onChange={handlePlaceChange} required />
              <input name="latitude" type="number" step="0.000001" placeholder="Latitude" value={placeForm.latitude} onChange={handlePlaceChange} required />
              <input name="longitude" type="number" step="0.000001" placeholder="Longitude" value={placeForm.longitude} onChange={handlePlaceChange} required />
              <input name="h3Index" placeholder="H3 index" value={placeForm.h3Index} onChange={handlePlaceChange} required />
              <label className="admin-check"><input name="isFeatured" type="checkbox" checked={placeForm.isFeatured} onChange={handlePlaceChange} /> Featured</label>
              <label className="admin-check"><input name="isVerified" type="checkbox" checked={placeForm.isVerified} onChange={handlePlaceChange} /> Verified</label>
              <button type="submit" disabled={placeLoading}>{placeLoading ? "Creating..." : "Create Place"}</button>
            </form>
            {placeMessage ? <p className="explore-note">{placeMessage}</p> : null}
          </section>
        </div>
      </div>
    </main>
  );
}

export default Admin;
