import { BrowserRouter, Routes, Route } from "react-router-dom";

import Home from "./pages/Home";
import Tours from "./pages/Tours";
import Food from "./pages/Food";
import Entertainment from "./pages/Entertainment";
import Security from "./pages/Security";
import Login from "./pages/Login";
import Register from "./pages/Register";
import TourDetails from "./pages/TourDetails";
import PlaceDetails from "./pages/PlaceDetails";
import Profile from "./pages/Profile";
import Admin from "./pages/Admin";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/tours" element={<Tours />} />
        <Route path="/food" element={<Food />} />
        <Route path="/entertainment" element={<Entertainment />} />
        <Route path="/security" element={<Security />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/profile" element={<Profile />} />
        <Route path="/admin" element={<Admin />} />
        <Route path="/tours/:id" element={<TourDetails />} />
        <Route path="/places/:id" element={<PlaceDetails />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
