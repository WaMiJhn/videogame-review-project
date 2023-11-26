  import axios from "axios";
  import TokenManager from "./TokenManager";

  const REVIEWS_BASE_URL = "http://localhost:8080/reviews";

  const ReviewsAPI = {
    createReview: (reviewData) => {
      const url = `${REVIEWS_BASE_URL}`;

      // Get the access token from TokenManager
      const accessToken = TokenManager.getAccessToken();

      // Set the headers with the access token
      const headers = {
        Authorization: `Bearer ${accessToken}`,
      };

      return axios.post(url, reviewData, { headers })
        .then((response) => response.data);
    },
    getReviewsByGameId: (gameId) => {
      const url = new URL(REVIEWS_BASE_URL);
      url.searchParams.append("gameId", gameId);

      return axios.get(url.toString())
        .then((response) => response.data.reviews);
    },
    getFilteredReviewsByGameId: (gameId, userId) => {
      const url = new URL(REVIEWS_BASE_URL);
      url.searchParams.append("gameId", gameId);
    
      // Get the access token from TokenManager
      const accessToken = TokenManager.getAccessToken();
    
      // Set the headers with the access token
      const headers = {
        Authorization: `Bearer ${accessToken}`,
      };
    
      return axios
        .get(url.toString(), { headers })
        .then((response) => {
          // Filter out the reviews of the logged-in user
          const reviews = response.data.reviews.filter(
            (review) => review.user.id !== userId
          );
          return reviews;
        })
        .catch((error) => {
          console.error(error);
        });
    },
    getReviews: (gameId, username, page, size) => {
      const params = new URLSearchParams();
      gameId && params.append("gameId", gameId);
      username && params.append("username", username);
      page && params.append("page", page);
      size && params.append("size", size);

      const url = `${REVIEWS_BASE_URL}?${params.toString()}`;
    
      return axios.get(url).then((response) => response.data.reviews);
    }, 
    updateReview: (reviewId, reviewData) => {
      const url = `${REVIEWS_BASE_URL}/${reviewId}`;

      // Get the access token from TokenManager
      const accessToken = TokenManager.getAccessToken();

      // Set the headers with the access token
      const headers = {
        Authorization: `Bearer ${accessToken}`,
      };

      return axios.put(url, reviewData, { headers })
        .then((response) => response.data);
    },
    deleteReview: (reviewId) => {
      const url = `${REVIEWS_BASE_URL}/${reviewId}`;

      // Get the access token from TokenManager
      const accessToken = TokenManager.getAccessToken();

      // Set the headers with the access token
      const headers = {
        Authorization: `Bearer ${accessToken}`,
      };

      return axios.delete(url, { headers })
        .then((response) => response.data);
    },
    getCountReviewsByGameId: (gameId) => {
      const url = `${REVIEWS_BASE_URL}/${gameId}/count-reviews-by-game`;
      return axios.get(url).then((response) => response.data);
    },
    getCountReviewsByUser: (username) => {
      const url = `${REVIEWS_BASE_URL}/${username}/count-reviews-by-user`;
      return axios.get(url).then((response) => response.data);
    }
  };

  export default ReviewsAPI;
