import React, { useState } from 'react';
import "../css/progressbar.css"

const ProgressBar = ({ rating, setRating }) => {
    const handleRatingChange = (value) => {
      setRating(value);
    };

  const getProgressBarColor = () => {
    if (rating >= 0 && rating <= 4) {
      return '#f00'; // Red
    } else if (rating >= 5 && rating <= 7) {
      return '#fc3'; // Yellow/Orange
    } else if (rating >= 8 && rating <= 10) {
      return '#6c3'; // Green
    }
  };

  return (
    <div className="progress-container">
      <div className="progress-bar">
        <div className="progress" style={{ width: `${(rating / 10) * 100}%`, backgroundColor: getProgressBarColor() }}></div>
      </div>
      <div className="rating-labels">
        {[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10].map((value) => (
          <div
            key={value}
            className={`rating-label ${rating === value ? 'selected' : ''}`}
            data-rating={value}
            onClick={() => handleRatingChange(value)}
            style={{ color: rating === value ? getProgressBarColor() : '' }}
          >
            {value}
          </div>
        ))}
      </div>
    </div>
  );
};

export default ProgressBar;
