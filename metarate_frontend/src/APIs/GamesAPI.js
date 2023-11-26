import axios from "axios";
import TokenManager from "./TokenManager";

const GAMES_BASE_URL = "http://localhost:8080/games";

const GamesAPI = {
  getGames: (genre, title, page, size) => {
    const params = new URLSearchParams();

    genre && params.append("genre", genre);
    title && params.append("title", title);
    page && params.append("page", page);
    size && params.append("size", size);

    const url = `${GAMES_BASE_URL}?${params.toString()}`;

    return axios.get(url).then((response) => response.data.games);
  },
  getGameById: (id) => {
    const url = `${GAMES_BASE_URL}/${id}`;

    return axios.get(url).then((response) => response.data);
  },
  createGame: (gameData) => {
    const url = `${GAMES_BASE_URL}`;

    // Get the access token from TokenManager
    const accessToken = TokenManager.getAccessToken();

    // Set the headers with the access token
    const headers = {
      Authorization: `Bearer ${accessToken}`,
    };

    return axios.post(url, gameData, { headers })
      .then((response) => response.data);
  },
  deleteGame: (gameId) => {
    const url = `${GAMES_BASE_URL}/${gameId}`;

    // Get the access token from TokenManager
    const accessToken = TokenManager.getAccessToken();

    // Set the headers with the access token
    const headers = {
      Authorization: `Bearer ${accessToken}`,
    };

    return axios.delete(url, { headers })
      .then((response) => response.data);
  },
  updateGame: (gameId, gameData) => {
    const url = `${GAMES_BASE_URL}/${gameId}`;

    // Get the access token from TokenManager
    const accessToken = TokenManager.getAccessToken();

    // Set the headers with the access token
    const headers = {
      Authorization: `Bearer ${accessToken}`,
    };

    return axios.put(url, gameData, { headers })
      .then((response) => response.data);
  },
  getAverageRating: (gameId) => {
    const url = `${GAMES_BASE_URL}/${gameId}/average-rating`;

    return axios.get(url).then((response) => response.data);
  },
  getCountGames: (genre, title) => {
    const params = new URLSearchParams();
  
    genre && params.append("genre", genre);
    title && params.append("title", title);
    
    const url = `${GAMES_BASE_URL}/count?${params.toString()}`;
    return axios.get(url).then((response) => response.data);
  },  
  getTopRatedGames: () => {
    const url = `${GAMES_BASE_URL}/top-rated`;

    return axios.get(url)
      .then((response) => response.data.games);
  },
};

export default GamesAPI;
