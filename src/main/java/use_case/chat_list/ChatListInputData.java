package use_case.chat_list;

import entity.User;

public class ChatListInputData {
    private final User user;
    private final String otherUser;

    public ChatListInputData(User user, String otherUser) {
        this.user = user;
        this.otherUser = otherUser;
    }

    public User getUser() {
        return user;
    }

    public String getUsername() {
        return user.getName();
    }

    public String getOtherUser() {
        return otherUser;
    }

}
