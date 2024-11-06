package entity;

import java.util.List;

public class Chat {
    private final List<Message> chatLog = new List<Message>();
    private final List<String> users;

    public Chat(List<String> users) {
        this.users = users;
    }

    public void addMessage(Message message) {
        chatLog.add(message);
    }

    public List<Message> getMessages() {
        return chatLog;
    }

    public List<String> getUsers() {
        return users;
    }
}
