import "./App.css";
import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import Navbar from "./components/common/Navbar/Navbar";
import Footer from "./components/common/Footer/Footer";
import RegisterPage from "./components/authentication/RegisterPage/RegisterPage";
import LoginPage from "./components/authentication/LoginPage/LoginPage";
import HomePage from "./components/pages/HomePage/HomePage";
import FindFlightsPage from "./components/pages/FindFlightsPage/FindFlightsPage";
import ProfilePage from "./components/profile/ProfilePage/ProfilePage";
import { RouteGuard } from "./services/RouteGuard";
import UpdateProfilePage from "./components/profile/UpdateProfilePage/UpdateProfilePage";
import BookingPage from "./components/pages/Booking/BookingPage/BookingPage";
import BookingDetailsPage from "./components/pages/Booking/BookingDetailsPage/BookingDetailsPage";

function App() {
  return (
    <BrowserRouter>
      <Navbar />

      <div className="content">
        <Routes>
          {/* AUTHENTICATION PAGES */}
          <Route path="/register" element={<RegisterPage />} />
          <Route path="/login" element={<LoginPage />} />

          {/* PUBLIC PAGES */}
          <Route path="/home" element={<HomePage />} />
          <Route path="/flights" element={<FindFlightsPage />} />

           {/* CUSTOMER PAGES */}
          <Route path="/profile" element={<RouteGuard allowedRoles={["CUSTOMER"]} element={<ProfilePage/>}/>}/>
           <Route path="/update-profile" element={<RouteGuard allowedRoles={["CUSTOMER"]} element={<UpdateProfilePage/>}/>}/>

          {/* BOOKING PAGES */}
          <Route path="/book-flight/:id" element={<RouteGuard allowedRoles={["CUSTOMER", "ADMIN", "PILOT"]} element={<BookingPage/>}/>}/>
          <Route path="/booking/:id" element={<RouteGuard allowedRoles={["CUSTOMER", "ADMIN", "PILOT"]} element={<BookingDetailsPage/>}/>}/>
          
          {/* Fallback for unmatched routes */}
          <Route path="*" element={<Navigate to="/home" />} />
        </Routes>
      </div>

      <Footer />
    </BrowserRouter>
  );
}

export default App;
