package entity;

import java.util.ArrayList;

public class ChatHistory {
    private final ArrayList<Message> chatLog = new ArrayList<Message>();
    private ArrayList<User> users = new ArrayList<User>();

    public ChatHistory(ArrayList<User> users) {
        this.users = users;
    }

    public void addMessage(Message message) {
        chatLog.add(message);
    }

    public ArrayList<Message> getMessages() {
        return chatLog;
    }

    public ArrayList<User> getUsers() {
        return users;
    }
}
