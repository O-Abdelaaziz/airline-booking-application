import "./App.css";
import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import Navbar from "./components/common/Navbar/Navbar";
import Footer from "./components/common/Footer/Footer";
import RegisterPage from "./components/authentication/RegisterPage/RegisterPage";
import LoginPage from "./components/authentication/LoginPage/LoginPage";
import HomePage from "./components/pages/HomePage/HomePage";

function App() {
  return (
    <BrowserRouter>
      <Navbar />

      <div className="content">
        <Routes>
          <Route path="/register" element={<RegisterPage />} />
          <Route path="/login" element={<LoginPage />} />

          {/* PUBLIC PAGES */}
          <Route path="/home" element={<HomePage />} />

          {/* Fallback for unmatched routes */}
          <Route path="*" element={<Navigate to="/home" />} />
        </Routes>
      </div>

      <Footer />
    </BrowserRouter>
  );
}

export default App;
