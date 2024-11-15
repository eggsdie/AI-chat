package entity;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ChatEntry {
    private String name;
    private LocalTime time;
    private String messagePreview;

    public ChatEntry(String name, LocalTime time, String messagePreview) {
        this.name = name;
        this.time = time;
        this.messagePreview = messagePreview;
    }

    public String getName() {
        return name;
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

    // Returns a formatted string of the last message time
    public String getLastMessageTime() {
        return time != null
                ? time.format(DateTimeFormatter.ofPattern("HH:mm"))
                : "No time available";
    }
}
