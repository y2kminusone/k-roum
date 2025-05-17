import React from 'react';
import Header from '../../components/common/Header';
import SearchSection from '../../components/ui/homePage/SearchSection';
import TrendingHashtags from '../../components/ui/homePage/TrendingHashtags';
import FeaturedContent from '../../components/ui/homePage/FeaturedContent';

const HomePage = () => {
  return (
    <div className="min-h-screen bg-white">
      <Header />
      <main className="container mx-auto px-4 py-8">
        <SearchSection />
        <TrendingHashtags />
        <FeaturedContent />
      </main>
    </div>
  );
};

export default HomePage;