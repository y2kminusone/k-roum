import React from 'react';
import Header from '../../components/common/Header';
import SearchSection from './SearchSection';
import TrendingHashtags from './TrendingHashtags';
import FeaturedContent from './FeaturedContent';

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