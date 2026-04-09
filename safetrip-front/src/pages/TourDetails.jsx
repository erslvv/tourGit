import { useParams } from "react-router-dom";

function TourDetails() {
  const { id } = useParams();

  return (
    <div style={{ padding: "40px" }}>
      <h1>Tour Details</h1>
      <p>Tour ID: {id}</p>

      <p>
        Here will be full information about the tour: description, location,
        price, etc.
      </p>
    </div>
  );
}

export default TourDetails;