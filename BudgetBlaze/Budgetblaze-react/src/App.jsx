import "./App.css";
import Footer from "./components/Footer";
import LandingPage from "./components/LandingPage";
import Navbar from "./components/Navbar";
import { Routes, Route, BrowserRouter } from "react-router-dom";
import RegisterPage from "./components/RegisterPage";
import { Toaster } from "react-hot-toast";
import LoginPage from "./components/LoginPage";
import Profile from "./components/Profile";
import PrivateRoute from "./PrivateRoute";
import ErrorPage from "./components/ErrorPage";

function App() {
  return (
    <div className="bg-[##fdf6f0]">
      <BrowserRouter>
        <Navbar />
        <Toaster position="top-center" />
        <Routes>
          <Route path="/" element={<LandingPage />} />
          <Route
            path="/profile"
            element={
              <PrivateRoute publicPage={false}>
                <Profile />
              </PrivateRoute>
            }
          />
          <Route
            path="/register"
            element={
              <PrivateRoute publicPage={true}>
                <RegisterPage />
              </PrivateRoute>
            }
          />
          <Route
            path="/login"
            element={
              <PrivateRoute publicPage={true}>
                <LoginPage />
              </PrivateRoute>
            }
          />
          <Route
            path="/error"
            element={<ErrorPage message="Error Occurred" />}
          />
        </Routes>
        <Footer />
      </BrowserRouter>
    </div>
  );
}

export default App;

// Creds -> tikamo1928@baxima.com, securepass123
