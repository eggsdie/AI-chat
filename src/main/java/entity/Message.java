package entity;

import java.time.LocalTime;

public class Message {

    private String content;
    private LocalTime time;

    public Message(String content, LocalTime time) {
        this.content = content;
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void getTime(LocalTime time) {
        this.time = time;
    }
}
