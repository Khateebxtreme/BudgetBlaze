import './App.css'
import Footer from './components/Footer';
import LandingPage from './components/LandingPage';
import Navbar from './components/Navbar'
import { Routes, Route, BrowserRouter } from "react-router-dom";
import RegisterPage from './components/RegisterPage';
import { Toaster } from "react-hot-toast";
import LoginPage from './components/LoginPage';

function App() {

  return (
    <div className='bg-[##fdf6f0]'>
      <BrowserRouter>
        <Navbar/>
          <Toaster position="top-center" />
          <Routes>
          <Route path="/" element={<LandingPage />} />
          <Route path="/register" element={<RegisterPage />} />
          <Route path="/login" element={<LoginPage />} />
          </Routes>
        <Footer/>
      </BrowserRouter>
    </div>
  )
}

export default App
