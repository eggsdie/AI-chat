package entity;

import java.util.List;

public class Chat {
    private final List<Message> chatLog = new List<Message>();
    private final List<User> users;
    private final String name;

    public Chat(List<User> users, String name) {
        this.users = users;
        this.name = name;
    }

    public void addMessage(Message message) {
        chatLog.add(message);
    }

    public List<Message> getMessages() {
        return chatLog;
    }

    public List<User> getUsers() {
        return users;
    }
}
