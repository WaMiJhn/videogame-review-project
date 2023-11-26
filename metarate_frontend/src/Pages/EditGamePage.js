import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import GamesAPI from "../APIs/GamesAPI";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import CreateGameForm from "../Components/GameForm";

const EditGamePage = () => {
  const { id } = useParams();
  const [game, setGame] = useState(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    GamesAPI.getGameById(id)
      .then((game) => {
        setGame(game);
        setIsLoading(false);
      })
      .catch((error) => {
        console.error(error);
        setIsLoading(false);
      });
  }, [id]);

  if (isLoading) {
    return <div>Loading...</div>;
  }

  if (!game) {
    return <div>Game not found</div>;
  }


  return (
    <section id="editGamePage">
    {/* Pass the game prop to the CreateGameForm component */}
    <CreateGameForm game={game} />
  </section>
  );
};

export default EditGamePage;
