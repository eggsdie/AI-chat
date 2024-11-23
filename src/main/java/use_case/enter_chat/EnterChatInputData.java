package use_case.enter_chat;

import entity.ChatEntry;

public class EnterChatInputData {
    private final ChatEntry chatEntry;

    public EnterChatInputData(ChatEntry chatEntry) {
        this.chatEntry = chatEntry;
    }

    public ChatEntry getChatEntry() {
        return chatEntry;
    }
}
