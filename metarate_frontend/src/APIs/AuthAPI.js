import axios from "axios";
import TokenManager from "./TokenManager";

const AuthAPI = {
    login: (username, password) => axios.post('http://localhost:8080/login', { username, password })
        .then(response => response.data.accessToken)
        .then(accessToken => TokenManager.setAccessToken(accessToken))
}

export default AuthAPI;