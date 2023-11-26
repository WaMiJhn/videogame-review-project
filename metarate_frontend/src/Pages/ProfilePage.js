import React, { useState, useEffect } from "react";
import TokenManager from "../APIs/TokenManager";
import { Link, useNavigate, useParams } from "react-router-dom";
import UserAPI from "../APIs/UserAPI";
import "../css/ProfilePage.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPenToSquare, faDeleteLeft, faChevronRight, faChevronLeft } from "@fortawesome/free-solid-svg-icons";
import getBackgroundColor from "../Components/GetBackgroundColorBasedOnValue";
import ReviewsAPI from "../APIs/ReviewsAPI";
import GamesAPI from "../APIs/GamesAPI";
import Popup from "reactjs-popup";
import ConfirmationForm from "../Components/ConfirmationForm";
import { toast } from "react-toastify";

const ProfilePage = () => {
  const { username } = useParams();
  const [user, setUser] = useState(null);
  const [claims, setClaims] = useState(TokenManager.getClaims());
  const [reviews, setReviews] = useState([]);
  const [averageRatings, setAverageRatings] = useState({});
  const [countReviews, setCountReviews] = useState(0);
  const [currentPage, setCurrentPage] = useState(1);
  const navigate = useNavigate();

  useEffect(() => {
    if (claims) {
      UserAPI.getUser(claims.id)
      .then(userData => setUser(userData))
      .catch(error => console.error(error));
    }
  }, []); 

  useEffect(() => {
    if (user) {
      ReviewsAPI.getCountReviewsByUser(user.username)
        .then((count) => {
          setCountReviews(count);
        })
        .catch((error) => {
          console.error(error);
        });
    }
  }, [user]);
    const pageSize = 5;

const goToPage = (pageNumber) => {
  setCurrentPage(pageNumber);
  // Adjust the page number to start from 0
  const adjustedPageNumber = pageNumber - 1;
  // Make an API call to fetch the reviews for the selected page
  ReviewsAPI.getReviews(null, user.username, adjustedPageNumber, pageSize)
    .then((reviews) => {
      // Update the reviews state variable with the fetched reviews
      setReviews(reviews);
    })
    .catch((error) => {
      console.error(error);
    });
};

const renderPageNumbers = () => {
  const totalPages = Math.ceil(countReviews / pageSize);
  const maxVisiblePages = 5;
  const currentPageIndex = currentPage - 1; // Adjust the current page index to start from 0
  const pageNumbers = [];

  let startPage = Math.max(0, currentPageIndex - Math.floor(maxVisiblePages / 2));
  let endPage = Math.min(totalPages - 1, startPage + maxVisiblePages - 1);

  if (endPage - startPage < maxVisiblePages - 1) {
    startPage = Math.max(0, endPage - maxVisiblePages + 1);
  }

  for (let i = startPage; i <= endPage; i++) {
    pageNumbers.push(i + 1); // Adjust the page number to start from 1
  }

  return (
    <>
      {currentPage > 1 && (
        <>
          <button onClick={() => goToPage(1)}>
            <FontAwesomeIcon icon={faChevronLeft} />
            <FontAwesomeIcon icon={faChevronLeft} />
          </button>
          <button onClick={() => goToPage(currentPage - 1)}>
            <FontAwesomeIcon icon={faChevronLeft} />
          </button>
        </>
      )}
      {pageNumbers.map((pageNumber) => (
        <button
          key={pageNumber}
          onClick={() => goToPage(pageNumber)}
          className={pageNumber === currentPage ? "active" : ""}
        >
          {pageNumber}
        </button>
      ))}
      {currentPage < totalPages && (
        <>
          <button onClick={() => goToPage(currentPage + 1)}>
            <FontAwesomeIcon icon={faChevronRight} />
          </button>
          <button onClick={() => goToPage(totalPages)}>
            <FontAwesomeIcon icon={faChevronRight} />
            <FontAwesomeIcon icon={faChevronRight} />
          </button>
        </>
      )}
    </>
  );
};


  const toastMessage = sessionStorage.getItem("toastMessage");

  if (toastMessage) {
    toast.success(toastMessage);
    sessionStorage.removeItem("toastMessage");
  }

  const handleDelete = (reviewId, event) => {
    event.preventDefault();

    ReviewsAPI.deleteReview(reviewId)
      .then(() => {
        sessionStorage.setItem("toastMessage", "Review deleted successfully.");
        window.location.reload();
      })
      .catch((error) => {
        console.error(error);
      });
  };

  useEffect(() => {
    if (user) {
      ReviewsAPI.getReviews(null, user.username, 0, pageSize)
        .then((reviews) => {
          setReviews(reviews);
  
          // Fetch average rating for each game
          reviews.forEach((review) => {
            const gameId = review.game.id;
            fetchAverageRating(gameId);
          });
        })
        .catch((error) => {
          console.error(error);
        });
    }
  }, [user]);

  const fetchAverageRating = async (gameId) => {
    try {
      const reviewCount = await ReviewsAPI.getCountReviewsByGameId(gameId);
      if (reviewCount === 0) {
        setAverageRatings((prevRatings) => ({
          ...prevRatings,
          [gameId]: "tbd", // No reviews available
        }));
      } else {
        const averageRating = await GamesAPI.getAverageRating(gameId);
        let formattedRating = Number(averageRating).toFixed(1);
        if (formattedRating.endsWith('.0')) {
          formattedRating = formattedRating.slice(0, -2); // Remove the decimal point and trailing zero
        }
        setAverageRatings((prevRatings) => ({
          ...prevRatings,
          [gameId]: formattedRating,
        }));
      }
    } catch (error) {
      console.error(error);
      setAverageRatings((prevRatings) => ({
        ...prevRatings,
        [gameId]: "0", // Set a default value in case of an error
      }));
    }
  };
  function formatDate(dateString) {
    const options = { month: 'short', day: 'numeric', year: 'numeric' };
    const date = new Date(dateString);
    const formattedDate = date.toLocaleDateString(undefined, options);
    const [month, day, year] = formattedDate.split(' ');
    return `${month} ${day}, ${year}`;
  }  

  return (
    <section id="profile">
      {user && (
        <div className="profile-container">
          <div className="profile-user">
            <div className="user-info">
              <div className="user-avatar">
                <img src={user.profilePic} alt="User Avatar" />
              </div>
              <div className="user-details">
                <h2 className="profile-user-name">{user.username}</h2>
                <div className="count-reviews">
                  <span className="count-reviews-number">{countReviews} </span>
                  <span>Reviews</span>
                </div>
              </div>
            </div>
            <div className="user-settings">
              <Link to="/settings">
                <button className="settings-edit">
                  Edit settings <span><FontAwesomeIcon icon={faChevronRight} /></span>
                </button>
              </Link>
            </div>
          </div>
          <div className="user-reviews">
            <h3>My scores</h3>
            <div className="user-review-container">
            {reviews.length > 0 ? (
  reviews.map((review) => (
                <div className="user-review-card" key={review.id}>
                  <div className="user-review-content">
                    <img src={review.game.imageUrl} alt="Game Image" />
                    <div
                      className="user-review-rating"
                      style={{ backgroundColor: getBackgroundColor(review.rating || 0) }}
                    >
                      <span>{review.rating}</span>
                    </div>
                    <div className="user-review-info">
                    <h4><Link to={`/games/${review.game.id}`}>{review.game.title}</Link></h4>
                      <div className="avg-review-score">
                        <h5>Average user score:{` `}
                          <span style={{ color: getBackgroundColor(averageRatings[review.game.id])}}>
                            {averageRatings[review.game.id]}
                          </span>
                        </h5>
                      </div>
                      <div className="user-review-date">{formatDate(review.date)}</div>
                      <div className="user-review-content">{review.content}</div>
                    </div>
                  </div>

                  <div className="user-review-actions">
                    <button className="edit"><Link className="edit" to={`/games/${review.game.id}`}><FontAwesomeIcon icon={faPenToSquare} /></Link></button>
                    <Popup 
                      trigger={<button className="delete"><FontAwesomeIcon icon={faDeleteLeft} /></button>}
                      modal
                      >
                      {(close) => 
                        <ConfirmationForm
                          onClose = {close}
                          onConfirm = {(event) => handleDelete(review.id, event)}
                          title = "Delete Review"
                          content = "Are you sure you want to delete this review?"
                        />}
                    </Popup>
                  </div>
                </div>
                ))
                ) : (
                  <h4 className="h4-no-reviews">You haven't written any reviews yet. Please go to the{` `}
                    <Link to={"/games"}>games</Link>{` `}
                    section to find a game for which you can write a review.</h4>
                )}
              <div className="pagination">
                {renderPageNumbers()}
              </div>
            </div>
          </div>
        </div>
      )}
      {!user && <p>Please log in to view the profile.</p>}
    </section>
  );
}

export default ProfilePage;