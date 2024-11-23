package use_case.chat_list;

import entity.User;

public class ChatListInputData {
    private final User currentUser;
    private final String otherUser;

    public ChatListInputData(User currentUser, String otherUser) {
        this.currentUser = currentUser;
        this.otherUser = otherUser;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public String getOtherUser() {
        return otherUser;
    }
}
