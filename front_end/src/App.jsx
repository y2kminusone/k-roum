import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import SearchSection from './SearchSection';
import SearchPage from './pages/Home/SearchPage';
import Header from './components/common/Header';

function App() {
  return (
    <Router>
      <Header /> {/* 모든 페이지에서 항상 보이게 함 */}
      <Routes>
        <Route path="/" element={<SearchSection />} />
        <Route path="/searchPage" element={<SearchPage />} />
      </Routes>
    </Router>
  );
}

export default App;
