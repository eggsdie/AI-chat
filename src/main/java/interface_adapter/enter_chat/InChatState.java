package interface_adapter.enter_chat;

import entity.ChatEntry;
import entity.Message;

import java.util.ArrayList;

public class InChatState {

    private ArrayList<Message> messages;
    private String sender;
    private String receiver;
    private boolean newMessage;

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public boolean isNewMessage() {
        return newMessage;
    }

    public void setNewMessage(boolean newMessage) {
        this.newMessage = newMessage;
    }

}
