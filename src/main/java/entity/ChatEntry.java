package entity;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ChatEntry {
    private User currentUser;
    private String otherUser;
    private LocalTime time;
    private String messagePreview;
    private final ArrayList<Message> messages = new ArrayList<>();

    public ChatEntry(User currentUser, String otherUser, LocalTime time, String messagePreview) {
        this.currentUser = currentUser;
        this.otherUser = otherUser;
        this.time = time;
        this.messagePreview = messagePreview;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public String getOtherUser() {
        return otherUser;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getMessagePreview() {
        return messagePreview;
    }

    // Returns a formatted preview of the last message
    public String getLastMessagePreview() {
        return messagePreview != null ? messagePreview : "No messages yet.";
    }

    public void addMessage(Message message) {
        this.messages.add(message);
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    // Returns a formatted string of the last message time
    public String getLastMessageTime() {
        return time != null
                ? time.format(DateTimeFormatter.ofPattern("HH:mm"))
                : "No time available";
    }
}
