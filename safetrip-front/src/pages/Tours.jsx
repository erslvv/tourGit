import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import api from "../api/axios";
import "./Tours.css";

function Tours() {
  const [tours, setTours] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchTours = async () => {
      try {
        const response = await api.get("/api/tours");
        console.log("Tours response:", response.data);
        setTours(response.data);
      } catch (err) {
        console.error(err);
        setError("Failed to load tours.");
      } finally {
        setLoading(false);
      }
    };

    fetchTours();
  }, []);

  if (loading) {
    return <div className="tours-page"><h2>Loading tours...</h2></div>;
  }

  if (error) {
    return <div className="tours-page"><h2>{error}</h2></div>;
  }

  return (
    <div className="tours-page">
      <div className="tours-header">
        <p className="tours-label">Safe and verified experiences</p>
        <h1>Explore Tours</h1>
        <p className="tours-subtext">
          Choose your next destination in Almaty and nearby places.
        </p>
      </div>

      <div className="tours-grid">
        {tours.length > 0 ? (
          tours.map((tour) => (
            <div className="tour-card" key={tour.id}>
              <img
                src={tour.imageUrl || "https://via.placeholder.com/400x250?text=Tour+Image"}
                alt={tour.title}
              />

              <div className="tour-card-content">
                <h3>{tour.title}</h3>
                <p>{tour.description || "No description available."}</p>

                <div className="tour-card-info">
                  <span>{tour.city}</span>
                  <span>{tour.price ? `${tour.price} ₸` : "Price not set"}</span>
                </div>

                <Link to={`/tours/${tour.id}`} className="tour-btn">
                  View Details
                </Link>
              </div>
            </div>
          ))
        ) : (
          <p>No tours found.</p>
        )}
      </div>
    </div>
  );
}

export default Tours;