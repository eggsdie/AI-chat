package use_case.enter_chat;

import entity.Message;

import java.util.ArrayList;

public class EnterChatOutputData {

    private final boolean useCaseFailed;
    private final String sender;
    private final String receiver;
    private final ArrayList<Message> messages;

    public EnterChatOutputData(boolean useCaseFailed, String sender, String receiver, ArrayList<Message> messages) {
        this.useCaseFailed = useCaseFailed;
        this.sender = sender;
        this.receiver = receiver;
        this.messages = messages;
    }

    public String getSender() {
        return this.sender;
    }

    public String getReceiver() {
        return this.receiver;
    }

    public ArrayList<Message> getMessages() {
        return this.messages;
    }
}
