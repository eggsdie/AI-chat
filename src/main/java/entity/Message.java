package entity;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Message {

    private User user;
    private String content;
    private LocalTime time;

    public Message(User user, String content, LocalTime time) {
        this.user = user;
        this.content = content;
        this.time = time;
    }

    public User getUser() {
        return user;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time != null
                ? time.format(DateTimeFormatter.ofPattern("HH:mm"))
                : "No time available";
    }
}
