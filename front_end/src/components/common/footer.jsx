import React from 'react';

const Footer = () => {
  return (
    <footer className="bg-gray-100 py-8 mt-12">
      <div className="container mx-auto px-4">
        <div className="flex flex-col md:flex-row justify-between items-center">
          <div className="mb-4 md:mb-0">
            <img src="/images/img_image.png" alt="K로움 Logo" className="w-[50px] h-[50px] rounded-full" />
            <h2 className="text-2xl font-['Jua'] text-[#0050ff] mt-2">K로움</h2>
          </div>
          
          <div className="flex flex-col md:flex-row space-y-4 md:space-y-0 md:space-x-8">
            <div>
              <h3 className="font-bold mb-2">About Us</h3>
              <ul className="space-y-1">
                <li><a href="#" className="hover:text-[#0050ff]">Our Story</a></li>
                <li><a href="#" className="hover:text-[#0050ff]">Team</a></li>
                <li><a href="#" className="hover:text-[#0050ff]">Careers</a></li>
              </ul>
            </div>
            
            <div>
              <h3 className="font-bold mb-2">Discover Korea</h3>
              <ul className="space-y-1">
                <li><a href="#" className="hover:text-[#0050ff]">Food</a></li>
                <li><a href="#" className="hover:text-[#0050ff]">Culture</a></li>
                <li><a href="#" className="hover:text-[#0050ff]">Travel</a></li>
                <li><a href="#" className="hover:text-[#0050ff]">Entertainment</a></li>
              </ul>
            </div>
            
            <div>
              <h3 className="font-bold mb-2">Contact</h3>
              <ul className="space-y-1">
                <li><a href="#" className="hover:text-[#0050ff]">Help Center</a></li>
                <li><a href="#" className="hover:text-[#0050ff]">Support</a></li>
                <li><a href="#" className="hover:text-[#0050ff]">Feedback</a></li>
              </ul>
            </div>
          </div>
        </div>
        
        <div className="mt-8 text-center text-gray-500">
          <p>© 2023 K로움. All rights reserved.</p>
        </div>
      </div>
    </footer>
  );
};

export default Footer;