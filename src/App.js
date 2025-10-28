import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import PassengerHome from "./pages/PassengerHome";
import DriverLogin from "./pages/DriverLogin";
import DriverMode from "./pages/DriverMode";
import Notifications from "./pages/Notifications";

export default function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<PassengerHome />} />
        <Route path="/driver/login" element={<DriverLogin />} />
        <Route path="/driver" element={<DriverMode />} />
        <Route path="/notifications" element={<Notifications />} />
      </Routes>
    </Router>
  );
}
