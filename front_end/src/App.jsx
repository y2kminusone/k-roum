// App.jsx (또는 App.tsx)
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import SearchSection from './SearchSection';
import SearchPage from './pages/Home/SearchPage';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<SearchSection />} />
        <Route path="/searchPage" element={<SearchPage />} />
      </Routes>
    </Router>
  );
}
export default App;