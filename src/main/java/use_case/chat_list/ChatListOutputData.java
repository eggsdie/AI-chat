package use_case.chat_list;

import entity.ChatEntry;

public class ChatListOutputData {

    private final boolean useCaseFailed;
    private final ChatEntry chatEntry;

    public ChatListOutputData(boolean useCaseFailed, ChatEntry chatEntry) {
        this.useCaseFailed = useCaseFailed;
        this.chatEntry = chatEntry;
    }

    public ChatEntry getChatEntry() {
        return chatEntry;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
