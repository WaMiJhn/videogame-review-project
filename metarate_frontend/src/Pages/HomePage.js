import React, { useState, useEffect, useRef } from 'react';
import GamesAPI from '../APIs/GamesAPI';
import '../css/Home.css';
import { Link } from 'react-router-dom';
import getBackgroundColor from '../Components/GetBackgroundColorBasedOnValue';
import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css';
import { Autoplay, Pagination, Navigation } from 'swiper';
import 'swiper/css/pagination';
import Slider from "react-slick";
import 'swiper/css/navigation';
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";


function HomePage() {
  const [topRatedGames, setTopRatedGames] = useState([]);
  const [averageRatings, setAverageRatings] = useState({});
  const [featuredGames, setFeaturedGames] = useState([]);
  const progressCircle = useRef(null);
  const progressContent = useRef(null);

  const featuredGamesSettings = {
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1
  };

  const genreSettings = {
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: 4,
    slidesToScroll: 4
  }

  useEffect(() => {
    GamesAPI.getGames(null, null, null, 4)
      .then((games) => {
        setFeaturedGames(games);
      })
      .catch((error) => {
        console.error('Error fetching featured games:', error);
      });
  },[]);

  useEffect(() => {
    // Fetch the top-rated games when the component mounts
    GamesAPI.getTopRatedGames()
      .then((games) => {
        setTopRatedGames(games);
        games.forEach((game) => {
          fetchAverageRating(game.id);
        });
      })
      .catch((error) => {
        console.error('Error fetching top-rated games:', error);
      });
  }, []);

  const fetchAverageRating = async (gameId) => {
    try {
      const averageRating = await GamesAPI.getAverageRating(gameId);
      let formattedRating = averageRating.toFixed(1);
      if (formattedRating.endsWith('.0')) {
        formattedRating = formattedRating.slice(0, -2); // Remove the decimal point and trailing zero
      }
      setAverageRatings((prevRatings) => ({
        ...prevRatings,
        [gameId]: formattedRating,
      }));
    } catch (error) {
      console.error(error);
      setAverageRatings((prevRatings) => ({
        ...prevRatings,
        [gameId]: '0', // Set a default value in case of an error
      }));
    }
  };

  const onAutoplayTimeLeft = (swiper, time, progress) => {
    if (progressCircle.current && progressContent.current) {
      progressCircle.current.style.setProperty('--progress', 1 - progress);
      progressContent.current.textContent = `${Math.ceil(time / 1000)}s`;
    }
  };

  return (
    <section id="home">
      <div className="featured-games">
      <h2>Featured Trailers</h2>
        <Slider {...featuredGamesSettings}>
          {featuredGames.map((game) => (
            <div className="featured-game-card" key={game.id}>
              <iframe
                src={`${game.trailerUrl}`}
                title="YouTube video player"
                allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                allowFullScreen
              />
              <h3><Link to={`/games/${game.id}`}>{game.title}</Link></h3>
              <div className='featured-game-description'>{game.description}</div>
            </div>
          ))}
        </Slider>
      </div>
      <div className="toprated-games">
        <h2>Top Rated Games</h2>
        <div className="toprated-games-container">
        <Swiper
          spaceBetween={10}
          slidesPerView={4}
          modules={[Autoplay, Pagination, Navigation]}
          autoplay={{
            delay: 2000,
            disableOnInteraction: true,
          }}
          pagination={{
            clickable: true,
          }}
          onAutoplayTimeLeft={onAutoplayTimeLeft}
          className="mySwiper"
          style={{ "--swiper-theme-color": "#000000" }} // Change the theme color here
        >
            {topRatedGames.map((game) => (
              <SwiperSlide key={game.id}>
                <Link to={`/games/${game.id}`} className="toprated-game-card">
                  <div className="game-banner">
                    <img src={game.imageUrl} alt={game.title} />
                    <div
                      className="toprated-rating"
                      style={{ backgroundColor: getBackgroundColor(averageRatings[game.id] || 0) }}
                    >
                      <span>{averageRatings[game.id]}</span>
                    </div>
                  </div>
                  <h2>{game.title}</h2>
                  <div className="toprated-game-description">
                    <span>{game.description}</span>
                  </div>
                </Link>
              </SwiperSlide>
            ))}
          </Swiper>
        </div>
      </div>
    </section>
  );
}

export default HomePage;
