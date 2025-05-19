import React, { useState } from 'react';

const SearchSection = () => {
  const [searchQuery, setSearchQuery] = useState('');

  const handleSearch = (e) => {
    e.preventDefault();
    console.log('Searching for:', searchQuery);
    // Implement search functionality here
  };

  return (
    <div className="flex justify-center mt-16 mb-8">
      <div className="relative w-full max-w-[609px]">
        <form onSubmit={handleSearch} className="w-full">
          <div className="flex items-center h-[80px] w-full bg-white rounded-[40px] shadow-[0px_4px_30px_rgba(0,0,0,0.25)]">
            <div className="flex items-center pl-6">
              <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M11 19C15.4183 19 19 15.4183 19 11C19 6.58172 15.4183 3 11 3C6.58172 3 3 6.58172 3 11C3 15.4183 6.58172 19 11 19Z" stroke="#919191" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
                <path d="M21 21L16.65 16.65" stroke="#919191" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
              </svg>
            </div>
            <input
              type="text"
              placeholder="ex) I like KPOP. Please recommend a famous place!"
              className="h-full flex-grow px-4 text-[24px] text-[#919191] font-['LG_PC'] focus:outline-none rounded-[40px]"
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
            />
          </div>
        </form>
      </div>
    </div>
  );
};

export default SearchSection;