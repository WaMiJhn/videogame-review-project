import React, { useEffect, useRef, useState } from 'react';
import "../css/chat.css";
import ChatMessage from "./ChatMessage";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPaperPlane } from "@fortawesome/free-solid-svg-icons";
import UserSearch from './UserSearch';

const ChatForm = (props) => {
  const [inputValue, setInputValue] = useState('');
  const [message, setMessage] = useState('');
  const [destinationUsername, setDestinationUsername] = useState('');
  const scrollContentRef = useRef(null);

  useEffect(() => {
    const scrollContent = scrollContentRef.current;

    // Scroll to the bottom initially
    scrollContent.scrollTop = scrollContent.scrollHeight;

    // Scroll to the bottom whenever new content is added
    const observer = new MutationObserver(() => {
      scrollContent.scrollTop = scrollContent.scrollHeight;
    });

    observer.observe(scrollContent, {
      childList: true,
      subtree: true,
    });

    return () => {
      observer.disconnect();
    };
  }, []);

  const handleInputChange = (event) => {
    setMessage(event.target.value);
    setInputValue(event.target.value);
  };

  const handleUserSearch = (username) => {
    console.log(username); // Log the selected username
    setDestinationUsername(username);
  };

  const getIconColor = () => {
    return inputValue.length > 0 ? 'white' : '#ccc';
  };

  const getBackgroundColor = () => {
    return inputValue.length > 0 ? '#ffcc34' : 'white';
  };

  const getCursorStyle = () => {
    return inputValue.length > 0 ? 'pointer' : 'auto';
  };

  const onSubmit = (event) => {
    event.preventDefault();
    props.onMessageSend({ text: message }, "chatroom");
    setInputValue('');
  };

  return (
    <section id="chat">
      <div className="chat-container">
        <div className="chat-header">
          <h1>Chat</h1>
        </div>
        <div className="chat-body">
          {/* <div className="sidebar">
            <div className="user-list">
              <div className="user-card">
                <img src="https://i.pinimg.com/originals/f1/0f/f7/f10ff70a7155e5ab666bcdd1b45b726d.jpg" alt="user" />
                <div className="user-name">username</div>
              </div>
              <div className="user-card">
                <img src="https://i.pinimg.com/originals/f1/0f/f7/f10ff70a7155e5ab666bcdd1b45b726d.jpg" alt="user" />
                <div className="user-name">username</div>
              </div>
            </div>
          </div> */}
          <form className="chat-content" onSubmit={onSubmit}>
            <div className="chat-top">
              <div className="user-info">
                <img src="https://cdn-icons-png.flaticon.com/512/62/62017.png" alt="user" />
                <div className="user-name">General chatroom</div>
              </div>
            </div>
            <div 
              className="scroll-content"
              style={{ overflowY: 'auto', maxHeight: '100%' }}
              ref={scrollContentRef}>
  {props.messagesReceived.map((message) => (
    <ChatMessage key={message.id} username={message.from} message={message.text} datetime={message.datetime} />
  ))}
</div>

            <div className="chat-input">
              <div className="input-box">
                <input
                  id="chatinput"
                  type="text"
                  placeholder="Type a message"
                  value={inputValue}
                  autoComplete='off'
                  required
                  onChange={handleInputChange}
                />
                <button className='chat-btn-send'
                  style={{color: getIconColor(),
                  backgroundColor: getBackgroundColor()}}>
                  <FontAwesomeIcon
                    id="paperplaneicon"
                    icon={faPaperPlane}
                    style={{
                      cursor: getCursorStyle(),
                    }}
                  />
                </button>
              </div>
            </div>
          </form>
        </div>
      </div>
    </section>
  );
};

export default ChatForm;
