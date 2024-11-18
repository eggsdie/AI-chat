package interface_adapter.add_friend;

public class ChatListState {

    private String username = "";
    // private String userDoesNotExistError;
    private String friendAlreadyAddedError;
    private String chatWithYourselfError;
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

//    public String getUserDoesNotExistError() {
//        return userDoesNotExistError;
//    }

    public String getFriendAlreadyAddedError() {
        return friendAlreadyAddedError;
    }

    public String getChatWithYourselfError() {
        return chatWithYourselfError;
    }

    public void setUsername(String username) {
        this.username = username;
    }

//    public void setUserDoesNotExistError(String userDoesNotExistError) {
//        this.userDoesNotExistError = userDoesNotExistError;
//    }

    public void setFriendAlreadyAddedError(String friendAlreadyAddedError) {
        this.friendAlreadyAddedError = friendAlreadyAddedError;
    }

    public void setChatWithYourselfError(String chatWithYourselfError) {
        this.chatWithYourselfError = chatWithYourselfError;
    }

    @Override
    public String toString() {
        return "AddFriendState{"
                + "username='" + username + '\''
                + '}';
    }

}
