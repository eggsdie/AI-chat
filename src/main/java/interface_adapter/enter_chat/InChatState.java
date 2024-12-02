package interface_adapter.enter_chat;

import java.util.ArrayList;

import entity.Message;

public class InChatState {

    private ArrayList<Message> messages;
    private String sender;
    private String receiver;
    private String senderPicture;
    private String receiverPicture;

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

    public String getSenderPicture() {
        return senderPicture;
    }

    public void setSenderPicture(String senderPicture) {
        this.senderPicture = senderPicture;
    }

    public String getReceiverPicture() {
        return receiverPicture;
    }

    public void setReceiverPicture(String receiverPicture) {
        this.receiverPicture = receiverPicture;
    }
}
