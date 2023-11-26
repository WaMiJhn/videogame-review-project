import React, { useEffect, useState } from 'react';
import '../css/chatMessage.css';
import UserAPI from '../APIs/UserAPI';
import TokenManager from '../APIs/TokenManager';

function ChatMessage(props) {
  const [user, setUser] = useState(null);
  const [claims, setClaims] = useState(TokenManager.getClaims());

  useEffect(() => {
    setClaims(TokenManager.getClaims());
    if (claims) {
      UserAPI.getUser(claims.id)
        .then(userData => setUser(userData))
        .catch(error => console.error(error));
    }
    else {
      setUser(null);
      console.error('No user found');
    }
  }, []); 



  function formatDateTime(datetime) {

    const date = new Date(datetime); // Convert the datetime string to a Date object
    const currentDate = new Date(); // Current date
    const yesterday = new Date(); // Yesterday's date
    yesterday.setDate(yesterday.getDate() - 1);

    // Check if the date is today
    if (
      date.getDate() === currentDate.getDate() &&
      date.getMonth() === currentDate.getMonth() &&
      date.getFullYear() === currentDate.getFullYear()
    ) {
      return `Today at ${date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}`;
    }

    // Check if the date is yesterday
    if (
      date.getDate() === yesterday.getDate() &&
      date.getMonth() === yesterday.getMonth() &&
      date.getFullYear() === yesterday.getFullYear()
    ) {
      return `Yesterday at ${date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}`;
    }

    // For other dates, return the formatted date and time
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    const year = String(date.getFullYear());
    const formattedDateTime = `${month}/${day}/${year} ${date.toLocaleTimeString([], {
      hour: '2-digit',
      minute: '2-digit'
    })}`;
    return formattedDateTime;
  }
  const messageStyle = {
    alignItems: user && user.username === props.username ? 'flex-end' : 'flex-start',
  };

  return (
    <div className="message" style={messageStyle}>
      <div className='message-info'>
      {props.username !== user?.username && <div>{props.username}</div>}
        <span id='currentDateTime'>{formatDateTime(props.datetime)}</span>
        {props.username === user?.username && <div>{props.username}</div>}
      </div>
      <div className='message-text'>{props.message}</div>
    </div>
  );
}

export default ChatMessage;
