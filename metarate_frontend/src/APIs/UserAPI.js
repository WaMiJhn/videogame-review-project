import axios from "axios";
import TokenManager from "./TokenManager";

const USER_BASE_URL = "http://localhost:8080/users";

const UserAPI = {
  getUser: (userId) => {
    // Make a GET request to fetch a specific user by ID
    return axios.get(`${USER_BASE_URL}/${userId}`, {
      headers: { Authorization: `Bearer ${TokenManager.getAccessToken()}` }
    })
      .then(response => response.data);
  },

  getUsers: (username) => {
    // Create URL query parameters using URLSearchParams
    const params = new URLSearchParams();
    username && params.append("username", username);

    // Append the query parameters to the base URL
    const url = `${USER_BASE_URL}?${params.toString()}`;

    // Get the access token from TokenManager
    const accessToken = TokenManager.getAccessToken();

    // Set the headers with the access token
    const headers = {
      Authorization: `Bearer ${accessToken}`
    };

    // Make a GET request to fetch users with optional username filter
    return axios.get(url, { headers })
      .then((response) => response.data.users);
  },
  createUser: (userdata) => {
    const url = `${USER_BASE_URL}`;
    // Make a POST request to create a new user
    return axios.post(url, userdata)
      .then(response => response.data);
  },
  updateUser: (userId, userdata) => {
    const url = `${USER_BASE_URL}/${userId}`;

    const accessToken = TokenManager.getAccessToken();

    const headers = {
      Authorization: `Bearer ${accessToken}`
    };

    // Make a PUT request to update a user's details
    return axios.put(url, userdata, { headers })
      .then(response => response.data);
  },
  updateUserPassword: (userId, userdata) => {
    const url = `${USER_BASE_URL}/${userId}/password`;

    const accessToken = TokenManager.getAccessToken();

    const headers = {
      Authorization: `Bearer ${accessToken}`
    };

    // Make a PUT request to update a user's password
    return axios.put(url, userdata, { headers })
      .then(response => response.data);
  }
};

export default UserAPI;
