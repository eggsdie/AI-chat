package use_case.enter_chat;

import entity.ChatEntry;

public class EnterChatOutputData {

    private final boolean useCaseFailed;
    private final ChatEntry chatEntry;

    public EnterChatOutputData(boolean useCaseFailed, ChatEntry chatEntry) {
        this.useCaseFailed = useCaseFailed;
        this.chatEntry = chatEntry;
    }

    public ChatEntry getChatEntry() {
        return this.chatEntry;
    }
}
