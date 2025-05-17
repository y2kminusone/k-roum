import { useLocation } from 'react-router-dom';

const SearchPage = () => {
  const location = useLocation();
  const { query, results } = location.state || {};

  return (
    <div className="flex h-screen">
      <div className="w-1/2 bg-gray-100">

      </div>

      <div className="w-1/2 p-8 overflow-y-auto">
        <h1 className="text-2xl font-bold mb-4">"{query}"에 대한 검색 결과</h1>
        {results && results.length > 0 ? (
          <ul className="space-y-4">
            {results.map((item, index) => (
              <li key={index} className="border p-4 rounded shadow">
                {item.title}
              </li>
            ))}
          </ul>
        ) : (
          <p className="text-gray-500">결과가 없습니다.</p>
        )}
      </div>
    </div>
  );
};

export default SearchPage;
