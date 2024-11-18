package interface_adapter.chat_list;

public class ChatListState {

    private String username = "";
    private String addFriendError;

    public String getAddFriendError() {
        return addFriendError;
    }

    public void setAddFriendError(String addFriendError) {
        this.addFriendError = addFriendError;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "AddFriendState{"
                + "username='" + username + '\''
                + '}';
    }

}
