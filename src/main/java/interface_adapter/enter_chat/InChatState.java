package interface_adapter.enter_chat;

import entity.ChatEntry;

import java.time.LocalTime;

public class InChatState {

    private ChatEntry chatEntry = new ChatEntry("", LocalTime.now(), "");

    public ChatEntry getChatEntry() {
        return chatEntry;
    }

    public void setChatEntry(ChatEntry chatEntry) {
        this.chatEntry = chatEntry;
    }

}
