import React, { useEffect, useState } from "react";
import { Link, useLocation } from "react-router-dom";
import "../css/style.css";
import TokenManager from "../APIs/TokenManager";
import LogoutButton from "./LogoutButton";

function NavBar() {
  const location = useLocation();
  const [isLoggedIn] = useState(!!TokenManager.getClaims());
  const [isAdmin] = useState(
    TokenManager.getClaims()?.roles?.includes("ADMIN") || false
  );

  useEffect(() => {
    const toggleActiveClass = () => {
      const navbarItems = document.querySelectorAll("#navbar-ul a");
      const currentPath = location.pathname;

      navbarItems.forEach((item) => {
        const itemPath = item.getAttribute("href");
        const isActive = currentPath.startsWith(itemPath);

        if (isActive) {
          item.classList.add("active");
        } else {
          item.classList.remove("active");
        }
      });

      navbarItems.forEach((item) => {
        item.addEventListener("click", function () {
          navbarItems.forEach((item) => item.classList.remove("active"));
          this.classList.add("active");
        });
      });
    };

    toggleActiveClass();
  }, [location.pathname]);

  return (
    <nav id="nav">
      <a className="logo" href="/">
        <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/2/20/Metacritic.svg/1024px-Metacritic.svg.png" alt="Metarate Logo" />
        <label>metarate</label>
      </a>
      <div>
        <ul id="navbar-ul">
          <li>
            <Link to="/games">Games</Link>
          </li>
          {isLoggedIn && (
            <>
              {isAdmin && (
                <li>
                  <Link to="/create">Create game</Link>
                </li>
              )}
              <li>
                <Link to="/chat">Chat</Link>
              </li>
              <li>
                <Link to="/profile">Profile</Link>
              </li>
              <li>
                <LogoutButton />
              </li>
            </>
          )}
          {!isLoggedIn && (
            <li>
              <Link to="/login">Sign in</Link>
            </li>
          )}
        </ul>
      </div>
    </nav>
  );
}

export default NavBar;
