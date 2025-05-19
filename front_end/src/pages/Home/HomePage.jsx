import React from 'react';
import Header from '../../components/common/Header';
import SearchSection from '../../components/ui/homePage/SearchSection';
import TrendingHashtags from '../../components/ui/homePage/TrendingHashtags';
import FeaturedContent from '../../components/ui/homePage/FeaturedContent';
import { useTranslation } from 'react-i18next';

const HomePage = () => {

   const { t } = useTranslation();

  return (
    <div className="min-h-screen bg-white">
      <main className="container mx-auto px-4 py-8">
        <SearchSection />
        <TrendingHashtags />
        <FeaturedContent />
      </main>
    </div>
  );
};

export default HomePage;