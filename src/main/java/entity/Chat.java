package entity;

import java.util.ArrayList;
import java.util.List;

public class Chat {
    private final List<Message> chatLog = new ArrayList<>();
    private final Friend friend;

    public Chat(Friend friend) {
        this.friend = friend;
    }

    public void addMessage(Message message) {
        chatLog.add(message);
    }

    public List<Message> getMessages() {
        return chatLog;
    }

    public Friend getFriend() {
        return friend;
    }
}
