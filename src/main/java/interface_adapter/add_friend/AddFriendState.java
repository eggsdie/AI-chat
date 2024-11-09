package interface_adapter.add_friend;

public class AddFriendState {

    private String username = "";
    private String userDoesNotExistError;
    private String friendAlreadyAddedError;

    public String getUsername() {
        return username;
    }

    public String getUserDoesNotExistError() {
        return userDoesNotExistError;
    }

    public String getFriendAlreadyAddedError() {
        return friendAlreadyAddedError;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserDoesNotExistError(String userDoesNotExistError) {
        this.userDoesNotExistError = userDoesNotExistError;
    }

    public void setFriendAlreadyAddedError(String friendAlreadyAddedError) {
        this.friendAlreadyAddedError = friendAlreadyAddedError;
    }

    @Override
    public String toString() {
        return "AddFriendState{"
                + "username='" + username + '\''
                + '}';
    }

}
