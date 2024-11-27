package use_case.send_message;

import entity.ChatEntry;
import entity.Message;

public class SendMessageInputData {
    private String sender;
    private String receiver;
    private String content;

    public SendMessageInputData(String sender, String receiver, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
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
}
