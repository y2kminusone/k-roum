// App.jsx (또는 App.tsx)
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import SearchSection from './SearchSection';
import ResultPage from './ResultPage'; 

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<SearchSection />} />
        <Route path="/results" element={<ResultPage />} />
      </Routes>
    </Router>
  );
}
export default App;