import React from 'react';
import PropTypes from 'prop-types';

const Card = ({ image, title, description, onClick }) => {
  return (
    <div 
      className="rounded-[30px] overflow-hidden shadow-md cursor-pointer transform transition-transform hover:scale-105"
      onClick={onClick}
    >
      <img 
        src={image} 
        alt={title} 
        className="w-full h-[250px] object-cover"
      />
      <div className="p-4">
        <h3 className="text-xl font-medium mb-2">{title}</h3>
        {description && <p className="text-gray-600">{description}</p>}
      </div>
    </div>
  );
};

Card.propTypes = {
  image: PropTypes.string.isRequired,
  title: PropTypes.string.isRequired,
  description: PropTypes.string,
  onClick: PropTypes.func
};

Card.defaultProps = {
  description: '',
  onClick: () => {}
};

export default Card;