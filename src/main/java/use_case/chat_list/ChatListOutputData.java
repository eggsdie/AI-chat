package use_case.chat_list;

import entity.ChatEntry;
import entity.User;

import java.util.ArrayList;

public class ChatListOutputData {

    private final boolean useCaseFailed;
    private final User activeUser;
    private final ArrayList<ChatEntry> chatList;

    public ChatListOutputData(boolean useCaseFailed, ArrayList<ChatEntry> chatList, User activeUser) {
        this.useCaseFailed = useCaseFailed;
        this.chatList = chatList;
        this.activeUser = activeUser;
    }

    public ArrayList<ChatEntry> getChatList() {
        return chatList;
    }

    public User getActiveUser() {
        return activeUser;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
