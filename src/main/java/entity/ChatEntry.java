package entity;

import java.util.ArrayList;

public class ChatEntry {
    private final String user1;
    private final String user2;
    private String time;
    private String messagePreview;
    private final ArrayList<Message> messages = new ArrayList<>();

    public ChatEntry(String user1, String user2, String time, String messagePreview) {
        this.user1 = user1;
        this.user2 = user2;
        this.time = time;
        this.messagePreview = messagePreview;
    }

    public String getUser1() {
        return user1;
    }

    public String getUser2() {
        return user2;
    }

    public String getTime() {
        return time;
    }

    // Returns a formatted preview of the last message
    public String getLastMessagePreview() {
        return messagePreview != null ? messagePreview : "No messages yet.";
    }

    public void addMessage(Message message) {
        this.messages.add(message);
        this.messagePreview = message.getContent();
        this.time = message.getTime();
    }

    public boolean matchesUsers(String userA, String userB) {
        return (user1.equals(userA) && user2.equals(userB)) || (user1.equals(userB) && user2.equals(userA));
    }
}
