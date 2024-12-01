package entity;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Message {

    private String id;
    private String sender;
    private String content;
    private ZonedDateTime time;
    private String receiver;

    public Message(String msgId, String sender, String content, String receiver, String time) {
        this.id = msgId;
        this.sender = sender;
        this.content = content;
        this.receiver = receiver;
        final OffsetDateTime parsedTime = OffsetDateTime.parse(time, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        this.time = parsedTime.atZoneSameInstant(ZoneId.systemDefault());
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
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
