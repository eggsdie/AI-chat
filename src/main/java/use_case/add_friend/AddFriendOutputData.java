package use_case.add_friend;

import java.util.ArrayList;

import entity.ChatEntry;
import entity.User;

public class AddFriendOutputData {

    private final boolean useCaseFailed;
    private final User activeUser;
    private final ArrayList<ChatEntry> chatList;

    public AddFriendOutputData(boolean useCaseFailed, ArrayList<ChatEntry> chatList, User activeUser) {
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
