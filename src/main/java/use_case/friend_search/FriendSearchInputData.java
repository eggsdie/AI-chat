package use_case.friend_search;

/**
 * The Input Data for the Logout Use Case.
 */
public class FriendSearchInputData {
    private String activeUser;

    public FriendSearchInputData(String activeUser) {
        this.activeUser = activeUser;
    }

    public String getActiveUser() {
        return activeUser;
    }
}
