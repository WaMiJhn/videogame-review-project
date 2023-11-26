import React from "react";
import ChatForm from "../Components/ChatForm";

const ChatPage = (props) => {
  return (
    <ChatForm onMessageSend={props.onMessageSend} messagesReceived = {props.messagesReceived}/>
  );
}

export default ChatPage;
