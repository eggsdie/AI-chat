package interface_adapter.chat_list;

import entity.User;

public class ChatListState {

    private User currentUser;
    private String otherUser = "";
    private String addFriendError;

    public String getAddFriendError() {
        return addFriendError;
    }

    public void setAddFriendError(String addFriendError) {
        this.addFriendError = addFriendError;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User username) {
        this.currentUser = username;
    }

    public String getOtherUser() {
        return otherUser;
    }

    public void setOtherUser(String username) {
        this.otherUser = username;
    }

    @Override
    public String toString() {
        return "AddFriendState{"
                + "username='" + otherUser + '\''
                + '}';
    }

}
