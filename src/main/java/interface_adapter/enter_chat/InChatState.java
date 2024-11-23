package interface_adapter.enter_chat;

import entity.ChatEntry;
import entity.Message;

public class InChatState {

    private ChatEntry chatEntry;

    public ChatEntry getChatEntry() {
        return chatEntry;
    }

    public void setChatEntry(ChatEntry chatEntry) {
        this.chatEntry = chatEntry;
    }

    public void addMessage(Message message) {
        this.chatEntry.addMessage(message);
    }

}
