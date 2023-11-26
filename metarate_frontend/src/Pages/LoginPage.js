import React, { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import "../css/loginpage.css";
import AuthAPI from "../APIs/AuthAPI";
import TokenManager from "../APIs/TokenManager";
import UserAPI from "../APIs/UserAPI";
import { toast } from "react-toastify";

const LoginPage = (props) => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [claims, setClaims] = useState(TokenManager.getClaims);
  const navigate = useNavigate();

  const handleSubmit = (event) => {
    event.preventDefault();
    handleLogin(username, password);
  };

  const handleLogin = (username, password) => {
    AuthAPI.login(username, password)
      .then(claims => {
        setClaims(claims);
        UserAPI.getUser(claims.id)
          .then(() => {
            sessionStorage.setItem('username', username);
            navigate("/profile");
            window.location.reload();
          })
          .catch(() => alert("Failed to retrieve user data!"));
      })
      .catch(() => toast.error("Invalid username or password!"))
      .catch(error => console.error(error));
  };
  

  return (
    <div className="container">
      <form onSubmit={handleSubmit} className="login-form">
        <h2>Log in to metarate</h2>
        <div className="form-group">
          <label htmlFor="username">Username:</label>
          <input
            type="text"
            value={username} 
            onChange={(e) => setUsername(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <label htmlFor="password">Password:</label>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        <button className="loginbtn" type="submit">Log in</button>
        <div className="register-link">
          Don't have an account? <Link to="/signup">Register</Link>
        </div>
      </form>
    </div>
  );
}

export default LoginPage;
