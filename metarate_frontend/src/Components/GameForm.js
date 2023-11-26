import React, { useState, useEffect } from "react";
import { useNavigate} from "react-router-dom";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "../css/creategameform.css";
import GenresAPI from "../APIs/GenresAPI";
import GamesAPI from "../APIs/GamesAPI";
import Popup from 'reactjs-popup';
import 'reactjs-popup/dist/index.css';
import ConfirmationForm from "./ConfirmationForm";

const CreateGameForm = ({ game }) => {
  const [title, setTitle] = useState("");
  const [developer, setDeveloper] = useState("");
  const [genreId, setGenreId] = useState("");
  const [releaseYear, setReleaseYear] = useState("");
  const [description, setDescription] = useState("");
  const [imageUrl, setImageUrl] = useState("");
  const [trailerUrl, setTrailerUrl] = useState("");
  const [genres, setGenres] = useState([]);
  const [showDeleteButton, setShowDeleteButton] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    if (game) {
      setTitle(game.title || "");
      setDeveloper(game.developer || "");
      setGenreId(game.genre.id || "");
      setReleaseYear(game.releaseYear ? game.releaseYear.toString() : "");
      setDescription(game.description || "");
      setImageUrl(game.imageUrl || "");
      setTrailerUrl(game.trailerUrl || "");
      setShowDeleteButton(true);
    }
    GenresAPI.getGenres()
      .then((genres) => {
        setGenres(genres);
      })
      .catch((error) => {
        console.error(error);
      });
  }, [game]);

  const handleSubmit = (e) => {
    e.preventDefault();

    // Create the game object
    const newGame = {
      title,
      developer,
      genreId: parseInt(genreId),
      releaseYear: parseInt(releaseYear),
      description,
      imageUrl,
      trailerUrl,
    };

    if (game) {
      // Update an existing game
      GamesAPI.updateGame(game.id, newGame)
        .then((updatedGame) => {
          console.log("Game updated:", updatedGame);
          // Show success toast notification
          toast.success("Game successfully updated!");
          navigate(`/games/${game.id}`)
        })
        .catch((error) => {
          console.error("Error updating game:", error);
          // Show error toast notification
          toast.error("Failed to update game. Please try again.");
        });
    } else {
      // Create a new game
      GamesAPI.createGame(newGame)
        .then((createdGame) => {
          console.log("Game created:", createdGame);
          // Show success toast notification
          toast.success("Game successfully created!");
          navigate(`/games/${createdGame.id}`);
        })
        .catch((error) => {
          console.error("Error creating game:", error);
          // Show error toast notification
          toast.error("Failed to create game. Please try again.");
        });
    }
  };

  const handleDelete = () => {
    GamesAPI.deleteGame(game.id)
      .then(() => {
        console.log("Game deleted:", game.id);
        // Show success toast notification
        toast.success("Game successfully deleted!");
        navigate("/games");
        // Navigate back or perform any necessary actions
      })
      .catch((error) => {
        console.error("Error deleting game:", error);
        // Show error toast notification
        toast.error("Failed to delete game. Please try again.");
      });
  };

  return (
    <div className="create-game-form">
      <h2>{game ? "Update Game" : "Create Game"}</h2>
      <form onSubmit={handleSubmit}>
        <label>
          Title:
          <input
            type="text"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            required
          />
        </label>
        <label>
          Developer:
          <input
            type="text"
            value={developer}
            onChange={(e) => setDeveloper(e.target.value)}
            required
          />
        </label>
        <label>
          Genre:
          <select
            value={genreId}
            onChange={(e) => setGenreId(e.target.value)}
            required={!game}
          >
            {!game && (
              <option value="" disabled>
                Select Genre
              </option>
            )}
            {genres.map((genre) => (
              <option key={genre.id} value={genre.id}>
                {genre.name}
              </option>
            ))}
          </select>
        </label>
        <label>
          Release Year:
          <input
            type="number"
            value={releaseYear}
            onChange={(e) => setReleaseYear(e.target.value)}
            required
          />
        </label>
        <label>
          Description:
          <textarea
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            required
          />
        </label>
        <label>
          Image URL:
          <input
            type="text"
            value={imageUrl}
            onChange={(e) => setImageUrl(e.target.value)}
            required
          />
        </label>
        <label>
          Trailer URL:
          <input
            type="text"
            value={trailerUrl}
            onChange={(e) => setTrailerUrl(e.target.value)}
            required
            pattern="^https?:\/\/(?:www\.)?youtube\.com\/embed\/[\w-]+$"
          />
        </label>
        <button type="submit" className="submitbtn">{game ? "Update" : "Create"}</button>
        {showDeleteButton && (
          <Popup trigger={<button type="button" className="deletebtn">
            Delete
          </button>} position={'right center'} modal>{(close) =>
           <ConfirmationForm 
            onConfirm={handleDelete} 
            onClose={close} 
            title={'Delete game'} 
            content={'Are you sure you want to delete this game?'}
            />}
          </Popup>
        )}
      </form>
      <ToastContainer />
    </div>
  );
};

export default CreateGameForm;
