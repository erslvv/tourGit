import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import api from "../api/axios";

function TourDetails() {
  const { id } = useParams();
  const [tour, setTour] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchTour = async () => {
      try {
        const response = await api.get(`/api/tours/${id}`);
        console.log("Tour details:", response.data);
        setTour(response.data);
      } catch (err) {
        console.error(err);
        setError("Failed to load tour details.");
      } finally {
        setLoading(false);
      }
    };

    fetchTour();
  }, [id]);

  if (loading) {
    return <div style={{ padding: "40px" }}><h2>Loading...</h2></div>;
  }

  if (error) {
    return <div style={{ padding: "40px" }}><h2>{error}</h2></div>;
  }

  if (!tour) {
    return <div style={{ padding: "40px" }}><h2>Tour not found.</h2></div>;
  }

  return (
    <div style={{ padding: "40px", maxWidth: "900px", margin: "0 auto" }}>
      <img
        src={tour.imageUrl || "https://via.placeholder.com/800x400?text=Tour+Image"}
        alt={tour.title}
        style={{
          width: "100%",
          borderRadius: "18px",
          marginBottom: "24px",
          maxHeight: "450px",
          objectFit: "cover",
        }}
      />

      <h1>{tour.title}</h1>
      <p><strong>City:</strong> {tour.city}</p>
      <p><strong>Price:</strong> {tour.price ? `${tour.price} ₸` : "Not specified"}</p>
      <p><strong>Description:</strong> {tour.description}</p>
    </div>
  );
}

export default TourDetails;