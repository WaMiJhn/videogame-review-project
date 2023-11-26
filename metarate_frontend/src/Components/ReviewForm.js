import React, { useEffect, useState } from 'react';
import ProgressBar from './Progressbar';
import ReviewsAPI from '../APIs/ReviewsAPI';
import UserAPI from '../APIs/UserAPI';
import TokenManager from '../APIs/TokenManager';
import { toast } from 'react-toastify';
import Popup from 'reactjs-popup';
import ConfirmationForm from './ConfirmationForm';

function ReviewForm({ gameId, review }) {
  const [rating, setRating] = useState(0);
  const [textareaValue, setTextareaValue] = useState('');
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [user, setUser] = useState(null);
  const [showDeleteButton, setShowDeleteButton] = useState(false);

  useEffect(() => {
    const claims = TokenManager.getClaims();
    setIsLoggedIn(!!claims);

    if (claims && claims.id) {
      UserAPI.getUser(claims.id)
        .then(userData => setUser(userData))
        .catch(error => console.error(error));
    }
  }, []);

  useEffect(() => {
    if (review) {
      setRating(review.rating);
      setTextareaValue(review.content);
      setShowDeleteButton(true);
    }
    else {
      setShowDeleteButton(false);
    }
  }, [review]);

  const handleRatingChange = (value) => {
    setRating(value);
  };

  const toastMessage = sessionStorage.getItem("toastMessage");

  if (toastMessage) {
    toast.success(toastMessage);
    sessionStorage.removeItem("toastMessage");
  }

  const handleTextareaChange = (event) => {
    setTextareaValue(event.target.value);
  };

  const handleSubmit = (event) => {
    event.preventDefault();

    if (!isLoggedIn) {
      console.error('User is not logged in.');
      return;
    }

    const reviewData = {
      gameId,
      userId: user.id,
      rating,
      content: textareaValue,
    };

    if (review) {
      // Update the existing review
      ReviewsAPI.updateReview(review.id, reviewData)
        .then(() => {
          sessionStorage.setItem('toastMessage', 'Review updated successfully!');
          window.location.reload();
        })
        .catch((error) => {
          console.error('Error updating review:', error);
        });
    } else {
      // Create a new review
      ReviewsAPI.createReview(reviewData)
        .then((createdReview) => {
          console.log('Review submitted:', createdReview);
          // Perform any other actions needed
          sessionStorage.setItem('toastMessage', 'Review submitted successfully!');
          window.location.reload();
        })
        .catch((error) => {
          console.error('Error submitting review:', error);
        });
    }
  };

  const handleDelete = (event) => {
    event.preventDefault();

    if (!review) {
      console.error('No review to delete.');
      return;
    }

    ReviewsAPI.deleteReview(review.id)
      .then(() => {
        sessionStorage.setItem('toastMessage', 'Review deleted successfully!');
        window.location.reload();
      })
      .catch((error) => {
        console.error('Error deleting review:', error);
      });
  };

  return (
    <form id="reviewform" onSubmit={handleSubmit}>
      <h3>Review this game</h3>
      <div className="inputform">
        <div className="review-score">
          <span>Your score:</span>
          <ProgressBar rating={rating} setRating={setRating} handleRatingChange={handleRatingChange} />
        </div>
        <div className="input-text">
          <textarea value={textareaValue} required onChange={handleTextareaChange}></textarea>
        </div>
        <div className={`buttons ${showDeleteButton ? 'two-btn' : ''}`}>
          {showDeleteButton && (<Popup trigger={<button type='button' className='review-btn' id='review-delete'>Delete</button>}
            modal
            position={'right center'}>
              {(close) =>
              <ConfirmationForm
                onConfirm={handleDelete}  
                onClose={close}
                title={'Delete review'} 
                content={'Are you sure you want to delete this review?'}
                />}
            </Popup>)}
          <button className='review-btn' id='review-submit' type="submit">Submit</button>
        </div>
      </div>
    </form>
  );
}

export default ReviewForm;
