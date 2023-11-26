import axios from "axios"

const GENRES_BASE_URL = "http://localhost:8080/genres";

const GenresAPI = {
    getGenres: () => axios.get(GENRES_BASE_URL)
        .then(response => response.data.genres)
};

export default GenresAPI