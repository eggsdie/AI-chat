package use_case.send_message;

import entity.ChatEntry;
import entity.Message;

public class SendMessageInputData {
    private final ChatEntry chatEntry;
    private final Message message;

    public SendMessageInputData(ChatEntry chatEntry, Message message) {
        this.chatEntry = chatEntry;
        this.message = message;
    }

    public ChatEntry getChatEntry() {
        return chatEntry;
    }

    public Message getMessage() {
        return message;
    }
}
