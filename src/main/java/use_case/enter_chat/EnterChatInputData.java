package use_case.enter_chat;

import entity.ChatEntry;

public class EnterChatInputData {
    private final String sender;
    private final String receiver;

    public EnterChatInputData(String sender, String receiver) {
       this.sender = sender;
       this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }
}
