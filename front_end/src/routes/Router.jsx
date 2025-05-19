import React from 'react';
import { Routes, Route } from 'react-router-dom';
import LandingPage from '../pages/LandingPage';
import LanguageSelect from '../pages/LanguageSelect';

const Router = () => {
  return (
    <Routes>
      <Route path="/" element={<LandingPage />} />
      <Route path="/language" element={<LanguageSelect />} />
    </Routes>
  );
};

export default Router;