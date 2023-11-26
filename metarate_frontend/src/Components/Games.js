import React, { useState, useEffect } from "react";
import GamesAPI from "./GamesAPI";

function Games() {
  const [games, setGames] = useState([]);

  useEffect(() => {
    GamesAPI.getGames()
      .then((data) => setGames(data))
      .catch((error) => console.log(error));
  }, []);

  return (
    <div>
      <h1>All Games</h1>
      {games.map((game) => (
        <div key={game.id}>
          <h2>{game.title}</h2>
          <p><strong>Developer:</strong> {game.developer}</p>
          <p><strong>Genre:</strong> {game.genre}</p>
          <p><strong>Rating:</strong> {game.rating}</p>
          <p><strong>Release Year:</strong> {game.releaseYear}</p>
          <img src={game.imageUrl} alt="game cover" />
          <p><strong>Description:</strong> {game.description}</p>
          <iframe title="game trailer" src={game.trailerUrl} />
        </div>
      ))}
    </div>
  );
}

export default Games;
