import React from 'react';
import PropTypes from 'prop-types';

const Tag = ({
  text,
  onClick,
  onRemove,
  color = 'blue',
  size = 'medium',
  removable = false,
  className = ''
}) => {
  const colors = {
    blue: 'bg-blue-100 text-blue-800',
    gray: 'bg-gray-100 text-gray-800',
    green: 'bg-green-100 text-green-800',
    red: 'bg-red-100 text-red-800',
    yellow: 'bg-yellow-100 text-yellow-800',
    purple: 'bg-purple-100 text-purple-800'
  };
  
  const sizes = {
    small: 'text-xs px-2 py-1',
    medium: 'text-sm px-3 py-1',
    large: 'text-base px-4 py-2'
  };
  
  const baseClasses = 'inline-flex items-center rounded-full font-medium';
  
  const tagClasses = `
    ${baseClasses}
    ${colors[color]}
    ${sizes[size]}
    ${className}
  `;
  
  return (
    <span className={tagClasses}>
      <span onClick={onClick} className={onClick ? 'cursor-pointer' : ''}>
        {text}
      </span>
      
      {removable && (
        <button
          type="button"
          className="ml-1.5 inline-flex items-center justify-center rounded-full hover:bg-opacity-10 hover:bg-gray-900 focus:outline-none"
          onClick={onRemove}
        >
          <svg className="w-3 h-3" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
            <path
              fillRule="evenodd"
              d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
              clipRule="evenodd"
            />
          </svg>
        </button>
      )}
    </span>
  );
};

Tag.propTypes = {
  text: PropTypes.string.isRequired,
  onClick: PropTypes.func,
  onRemove: PropTypes.func,
  color: PropTypes.oneOf(['blue', 'gray', 'green', 'red', 'yellow', 'purple']),
  size: PropTypes.oneOf(['small', 'medium', 'large']),
  removable: PropTypes.bool,
  className: PropTypes.string
};

export default Tag;