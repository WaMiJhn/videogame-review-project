import React, { useState, useEffect } from "react";
import { useNavigate, useLocation, Link, useSearchParams } from "react-router-dom";
import { faChevronLeft, faChevronRight } from '@fortawesome/free-solid-svg-icons';
import GamesAPI from "../APIs/GamesAPI";
import "../css/style.css";
import GenresAPI from "../APIs/GenresAPI";
import TokenManager from "../APIs/TokenManager";
import getBackgroundColor from "../Components/GetBackgroundColorBasedOnValue";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import ReviewsAPI from "../APIs/ReviewsAPI";

function truncateDescription(description, maxLength) {
  if (description.length <= maxLength) {
    return description;
  }

  const truncatedText = description.substr(0, maxLength);
  return truncatedText.substr(0, truncatedText.lastIndexOf(" ")) + "...";
}

function GamesPage() {
  const [games, setGames] = useState([]);
  const [searchParams] = useSearchParams();
  const [averageRatings, setAverageRatings] = useState({});
  const [genres, setGenres] = useState([]);
  const [searchTitle, setSearchTitle] = useState("");
  const [selectedGenre, setSelectedGenre] = useState("");
  const [currentPage, setCurrentPage] = useState(1);
  const [countGames, setCountGames] = useState(0);
  const navigate = useNavigate();
  const location = useLocation();
  const [isAdmin] = useState(
    TokenManager.getClaims()?.roles?.includes("ADMIN") || false
  );

  const pageSize = 5; // Number of games per page

  const renderPageNumbers = () => {
    const totalPages = Math.ceil(countGames / pageSize);
    const maxVisiblePages = 5;
    const currentPageIndex = currentPage - 1;
    const pageNumbers = [];
  
    let startPage = Math.max(0, currentPageIndex - Math.floor(maxVisiblePages / 2));
    let endPage = Math.min(totalPages - 1, startPage + maxVisiblePages - 1);
  
    if (endPage - startPage < maxVisiblePages - 1) {
      startPage = Math.max(0, endPage - maxVisiblePages + 1);
    }
  
    for (let i = startPage; i <= endPage; i++) {
      pageNumbers.push(i + 1);
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
  

  useEffect(() => {
    GenresAPI.getGenres()
      .then((genres) => {
        setGenres(genres);
      })
      .catch((error) => {
        console.error(error);
      });
  }, []);
  
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
  
  
  const fetchCountGames = async () => {
    try {
      const genre = searchParams.get("genre") || "";
      const title = searchParams.get("title") || "";
      const count = await GamesAPI.getCountGames(genre, title);
      setCountGames(count);
    } catch (error) {
      console.error(error);
    }
  };
  
  const fetchGames = () => {
    const queryParams = new URLSearchParams(location.search);
    const genre = queryParams.get("genre") || "";
    const title = queryParams.get("title") || "";
    const page = parseInt(queryParams.get("page")) || 0;

    setCurrentPage(page);
    setGames([]); // Reset games state

    const apiPage = page ? page - 1 : 0; // The API starts counting pages from 0

    GamesAPI.getGames(genre, title, apiPage, pageSize)
      .then((games) => {
        setGames(games);
        games.forEach((game) => fetchAverageRating(game.id));
      })
      .catch((error) => {
        console.error(error);
      });
  };
  
  
  useEffect(() => {
    fetchGames();
    fetchCountGames();
  }, [location.search]);

  const handleSearch = (e) => {
    e.preventDefault();
    const queryParams = new URLSearchParams();
  
    if (searchTitle) {
      queryParams.set("title", searchTitle);
    }
  
    if (selectedGenre) {
      queryParams.set("genre", selectedGenre);
    }
  
    queryParams.set("page", "1"); // Reset to the first page when searching
    navigate(`/games?${queryParams.toString()}`);
  };

  useEffect(() => {
    const queryParams = new URLSearchParams(location.search);
    const page = parseInt(queryParams.get("page")) || 1;
    setCurrentPage(page);
    const title = queryParams.get("title") || "";
    const genre = queryParams.get("genre") || "";

    setSearchTitle(title);
    setSelectedGenre(genre);
  }, [location.search]);

  const goToPage = (page) => {
    const queryParams = new URLSearchParams(location.search);
    queryParams.set("page", page.toString());
    navigate(`?${queryParams.toString()}`);
  };

  return (
    <section id="gamesPage">
      <div className="search-bar">
        <form onSubmit={handleSearch}>
          <input
            type="text"
            placeholder="Search by title"
            value={searchTitle}
            onChange={(e) => setSearchTitle(e.target.value)}
          />
          <select
            value={selectedGenre}
            onChange={(e) => setSelectedGenre(e.target.value)}
          >
            <option value="">All Genres</option>
            {genres.map((genre) => (
              <option key={genre.id} value={genre.name}>
                {genre.name}
              </option>
            ))}
          </select>
          <button type="submit">Search</button>
        </form>
      </div>
      <div className="games-container">
        {games.map((game) => (
          <div className="game-card" key={game.id}>
            <Link to={`/games/${game.id}`}><img src={game.imageUrl} alt={game.name} className="game-image" /></Link>
            <div className="game-details">
              <Link to={`/games/${game.id}`}>
                <h2>{game.title}</h2>
              </Link>
              <p>
                Genre:{" "}
                <Link to={`/games?genre=${game.genre.name}`}>
                  {game.genre.name}
                </Link>
              </p>
              <div className="game-description">
                {truncateDescription(game.description, 100)}
              </div>
            </div>
            {isAdmin && 
            <div className="gamecard-admin">
              <Link to={`/games/edit/${game.id}`}><button>Edit</button></Link>
            </div>}
            <div className="gamecard-rating" style={{ backgroundColor: getBackgroundColor(averageRatings[game.id]|| 0) }}>
            <span>{averageRatings[game.id]}</span>
            </div>
          </div>
        ))}
      </div>
      <div className="pagination">
        {renderPageNumbers()}
      </div>
    </section>
  );
}

export default GamesPage;
