import React from "react";
import "../css/Review.css";
import getBackgroundColor from "./GetBackgroundColorBasedOnValue";

const Review = ({ review}) => {
  const formatDate = (dateString) => {
    const options = { month: "long", day: "numeric", year: "numeric" };
    const date = new Date(dateString);
    return date.toLocaleDateString("en-US", options);
  };

  return (
    <div key={review.id} id="review">
      <div className="user">
        <div className="review-profile">
          <img
            src={review.user.profilePic}
            className="profile-picture"
            alt="Profile"
          />
          <div className="user-info">
            <p className="username">{review.user.username}</p>
            <p className="date">{formatDate(review.date)}</p>
          </div>
        </div>
        <div className="review-rating" style={{ backgroundColor: getBackgroundColor(review.rating || 0) }}>
          <span>{review.rating}</span>
        </div>
      </div>
      <div className="review-content">{review.content}</div>
      </div>
  );
};

export default Review;
