import './App.css';
import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom';
import Navbar from './components/common/Navbar/Navbar';
import Footer from './components/common/Footer/Footer';

function App() {

  return (
    <BrowserRouter>
      <Navbar />

      <div className="content">
        <Routes>

          {/* Fallback for unmatched routes */}
          <Route path="*" element={<Navigate to="/home"/>}/>
          
        </Routes>
      </div>

      <Footer />
     </BrowserRouter>
  )

}

export default App;
