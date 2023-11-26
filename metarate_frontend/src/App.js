import React, { useState, useEffect } from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import GamesPage from "./Pages/GamesPage";
import NavBar from "./Components/NavBar";
import "./css/style.css";
import LoginPage from "./Pages/LoginPage";
import HomePage from "./Pages/HomePage";
import ProfilePage from "./Pages/ProfilePage";
import GameDetailPage from "./Pages/GameDetailPage";
import TokenManager from "./APIs/TokenManager";
import NotFoundPage from "./Pages/NotFoundPage";
import CreateGamePage from "./Pages/CreateGamePage";
import EditGamePage from "./Pages/EditGamePage";
import { Client } from '@stomp/stompjs';
import { v4 as uuidv4 } from 'uuid';
import ChatForm from "./Components/ChatForm";
import SettingsPage from "./Pages/SettingsPage";
import RegisterPage from "./Pages/RegisterPage";
import UserAPI from "./APIs/UserAPI";



function Protected({ isLoggedIn, children }) {
  if (!isLoggedIn) {
    return <Navigate to="/login" replace />;
  }
  return children;
}

function AdminProtected({ isLoggedIn, isAdmin, children }) {
  if (!isLoggedIn || !isAdmin) {
    return <Navigate to="/login" replace />;
  }
  return children;
}

function AuthProtected({ isLoggedIn, children }) {
  if (isLoggedIn) {
    return <Navigate to="/" replace />;
  }
  return children;
}


function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(!!TokenManager.getClaims());
  const [isAdmin, setIsAdmin] = useState(
    TokenManager.getClaims()?.roles?.includes("ADMIN") || false
  );
  const [messagesReceived, setMessagesReceived] = useState([]);
  const [stompClient, setStompClient] = useState();
  const [username, setUsername] = useState(sessionStorage.getItem('username'));

  useEffect(() => {
    if (username) {
      setupStompClient('chatroom');
    }
  }, [username]);

  const sendMessage = (newMessage, chatroomTopic) => {
    const payload = { 
      'id': uuidv4(),
      'from': username, 
      'text': newMessage.text,
      'datetime': Date.now()
    };
    if (chatroomTopic) {
      stompClient.publish({ 'destination': `/topic/${chatroomTopic}`, body: JSON.stringify(payload) });
    } 
  };

  const onMessagesReceived = (message) => {
    const receivedMessage = JSON.parse(message.body);
    setMessagesReceived((prevMessages) => [...prevMessages, receivedMessage]);
  };


  let hasSubscribed = false;

  const setupStompClient = (chatroomTopic) => {
    // Stomp client over websockets
    const stompClient = new Client({
      brokerURL: 'ws://localhost:8080/ws',
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000
    });
    
    stompClient.onConnect = () => {
      if (!hasSubscribed) {
        stompClient.subscribe(`/topic/${chatroomTopic}`, onMessagesReceived);
        hasSubscribed = true;
      }
    };
  
    // Initiate client
    stompClient.activate();
  
    // Maintain the client for sending and receiving
    setStompClient(stompClient);
  };
  
  
  useEffect(() => {
    const checkTokenExpiration = () => {
      const tokenExpiration = TokenManager.getTokenExpiration();
      const currentTime = new Date().getTime() / 1000;

      if (tokenExpiration && currentTime >= tokenExpiration) {
        // Token has expired, log out the user and reload the page
        TokenManager.clear();
        setIsLoggedIn(false);
        setIsAdmin(false);
        window.location.reload();
      }
    };

    // Check token expiration on component mount and every 10 minutes
    checkTokenExpiration();
    const interval = setInterval(checkTokenExpiration, 600000);

    return () => clearInterval(interval); // Clean up interval on component unmount
  }, []);
  return (
    <div className="App">
      <Router>
        <NavBar />
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/games" element={<GamesPage />} />
          <Route path="/games/:id" element={<GameDetailPage />} />
          <Route path="/login" element={<LoginPage/>} />
          <Route
            path="/profile"
            element={
              <Protected isLoggedIn={isLoggedIn}>
                <ProfilePage />
              </Protected>
            }
          />
          <Route 
            path="/settings"
            element={
              <Protected isLoggedIn={isLoggedIn}>
                <SettingsPage />
              </Protected>
            }
          />
          <Route
            path="/create"
            element={
              <AdminProtected isLoggedIn={isLoggedIn} isAdmin={isAdmin}>
                <CreateGamePage />
              </AdminProtected>
            }
          />
          <Route
            path="/games/edit/:id"
            element={
              <AdminProtected isLoggedIn={isLoggedIn} isAdmin={isAdmin}>
                <EditGamePage />
              </AdminProtected>
            }
          />
          <Route
            path="/chat"
            element={
              <Protected isLoggedIn={isLoggedIn}>
                <ChatForm 
                onMessageSend={sendMessage}  
                messagesReceived = {messagesReceived}
                 />
              </Protected>
            }
          />
          <Route
            path="/signup"
            element={
              <AuthProtected isLoggedIn={isLoggedIn}>
                <RegisterPage />
              </AuthProtected>
            }
          />
          <Route path="/users/:username" element={<ProfilePage />} />
          <Route path="*" element={<NotFoundPage />} />
        </Routes>
      </Router>
      <ToastContainer />
    </div>
  );
}

export default App;
