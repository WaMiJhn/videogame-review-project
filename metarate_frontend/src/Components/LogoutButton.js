import React from "react";
import { useNavigate } from "react-router-dom";
import TokenManager from "../APIs/TokenManager";
import "../css/style.css";

function LogoutButton() {
  const navigate = useNavigate();

  const handleLogout = () => {
    TokenManager.clear(); // Clear access token and user information
    sessionStorage.clear();
    navigate("/login"); // Navigate to the login page
    window.location.reload();
  };

  return <button id="logoutbtn" onClick={handleLogout}>Sign out</button>;
}

export default LogoutButton;
