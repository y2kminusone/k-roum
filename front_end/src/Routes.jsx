import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

// Import page components
import HomePage from './pages/Home/HomePage';
import SearchPage from './pages/Home/SearchPage';

const AppRoutes = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/searchPage" element={<SearchPage />} />
      </Routes>
    </Router>
  );
};

export default AppRoutes;