import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import GamesAPI from "../APIs/GamesAPI";
import ReviewsAPI from "../APIs/ReviewsAPI";
import "../css/style.css";
import TokenManager from "../APIs/TokenManager";
import ReviewForm from "../Components/ReviewForm";
import getBackgroundColor from "../Components/GetBackgroundColorBasedOnValue";
import Review from "../Components/Review";
import UserAPI from "../APIs/UserAPI";
import { faChevronLeft, faChevronRight } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

function GameDetailPage() {
  const { id } = useParams();
  const [game, setGame] = useState(null);
  const [reviews, setReviews] = useState([]);
  const [averageRating, setAverageRating] = useState(0);
  const [countReviews, setCountReviews] = useState(0);
  const [isLoggedIn] = useState(!!TokenManager.getClaims());
  const [userReview, setUserReview] = useState([]);
  const [isExpanded, setIsExpanded] = useState(false); // State for "Expand" and "Collapse" feature
  const claims = TokenManager.getClaims();
  const [username, setUsername] = useState(claims && claims.sub ? claims.sub : null);
  const [currentPage, setCurrentPage] = useState(1);
  const pageSize = 5;

  useEffect(() => {
    if (username) {
      UserAPI.getUser(TokenManager.getClaims().id)
        .then((user) => {
          setUsername(user.username);
        })
        .catch((error) => {
          console.error(error);
        });
    }
  }, [username]);

  useEffect(() => {
    ReviewsAPI.getCountReviewsByGameId(id)
      .then((count) => {
        setCountReviews(count);
      })
      .catch((error) => {
        console.error(error);
      })
  
    GamesAPI.getGameById(id)
      .then((game) => {
        setGame(game);
      })
      .catch((error) => {
        console.error(error);
      });
  
    if (!isLoggedIn) {
      ReviewsAPI.getReviewsByGameId(id, null, 0, pageSize)
        .then((reviews) => {
          setReviews(reviews);
        })
        .catch((error) => {
          console.error(error);
        });
    } else {
  
      ReviewsAPI.getReviews(id, username)
        .then((userReviews) => {
          const userReview = userReviews.length > 0 ? userReviews[0] : null;
          setUserReview(userReview);
        })
        .catch((error) => {
          console.error(error);
        });
  
      ReviewsAPI.getReviews(id, null, 0, pageSize)
        .then((reviews) => {
          setReviews(reviews);
        })
        .catch((error) => {
          console.error(error);
        });
    }
  
    ReviewsAPI.getCountReviewsByGameId(id)
      .then((reviewCount) => {
        if (reviewCount === 0) {
          setAverageRating("tbd"); // No reviews available
        } else {
          GamesAPI.getAverageRating(id)
            .then((averageRating) => {
              let formattedRating = averageRating.toFixed(1);
              if (formattedRating.endsWith('.0')) {
                formattedRating = parseInt(formattedRating);
              }
              setAverageRating(formattedRating);
            })
            .catch((error) => {
              console.error(error);
            });
        }
      })
      .catch((error) => {
        console.error(error);
      });
  }, [id, isLoggedIn, username]);
  
  


  if (!game) {
    return (
      <div>
        <h1>Game Not Found</h1>
        <p>The requested game does not exist.</p>
      </div>
    )
  }

  const toggleDescription = () => {
    setIsExpanded((prevState) => !prevState);
  };

  const truncateDescription = (description, maxLength) => {
    if (description.length <= maxLength) {
      return description;
    }
    const truncatedText = description.slice(0, maxLength);
    return truncatedText.slice(0, truncatedText.lastIndexOf(" ")) + "...";
  };

  const goToPage = (pageNumber) => {
    setCurrentPage(pageNumber);
    // Adjust the page number to start from 0
    const adjustedPageNumber = pageNumber - 1;
    // Make an API call to fetch the reviews for the selected page
    ReviewsAPI.getReviews(id, null, adjustedPageNumber, pageSize)
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
  

  return (
    <section id="gameDetailPage">
      <div className="game">
        <div>
          <h2>{game.title}</h2>
          <div className="game-details">
            <p>{game.developer}</p>
            <p>{game.genre.name}</p>
          </div>
        </div>
        <div className="game-info">
          <img src={game.imageUrl} alt={game.title} className="game-image" />
          <div className="middle">
            <div className="rating">
              <div className="score" style={{ backgroundColor: getBackgroundColor(averageRating || 0) }}>
                <span>{averageRating}</span>
              </div>
              <div className="score-description">
                <label>Metarating</label>
                <p>Average score based on <span className="countReviews">{countReviews}</span> ratings</p>
              </div>
            </div>
            <div className="summary">
              <span className="sum">Summary: </span>
              <span>
              {isExpanded
            ? game.description
            : truncateDescription(game.description, 300)}
            {game.description.length > 300 && (
              <span id="descriptiontoggle" className={isExpanded ? 'expanded' : 'collapsed'} onClick={toggleDescription}>
                {isExpanded ? " Collapse" : " Expand"}
              </span>
            )}
              </span>
            </div>
          </div>
          <div className="small-game-details">
            <div className="row">
              <span className="for">Developer: </span>
              <span>{game.developer}</span>
            </div>
            <div className="row">
              <span className="for">Release year: </span>
              <span>{game.releaseYear}</span>
            </div>
            <div className="row">
              <span className="for">Genre: </span>
              <span>{game.genre.name}</span>
            </div>
          </div>
        </div>
      </div>
      <div className="trailer">
      <iframe width="560" height="315" 
      src={game.trailerUrl} 
      title="YouTube video player" 
      frameBorder="0" 
      allow="accelerometer; autoplay; clipboard-write; encrypted-media;
       gyroscope; picture-in-picture; web-share" 
       allowFullScreen>
       </iframe>
      </div>
      {isLoggedIn && (
        <div className="reviews-section">
          <ReviewForm gameId={game.id} review={userReview} />
      </div>
      )}
    <div className="reviews-section">
      <h3>Reviews</h3>
      {reviews.length > 0 ? (
        reviews.map((review) => (
          <Review key={review.id} review={review} />
        ))
      ) : (
        <p>There are no reviews for this game yet.</p>
      )}
      <div className="pagination">
        {renderPageNumbers()}
      </div>
    </div>
    </section>
  );
}

export default GameDetailPage;