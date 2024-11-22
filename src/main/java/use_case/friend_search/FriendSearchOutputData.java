package use_case.friend_search;

/**
 * Output Data for the Logout Use Case.
 */
public class FriendSearchOutputData {

    private String activeUser;
    private boolean useCaseFailed;

    public FriendSearchOutputData(String activeUser, boolean useCaseFailed) {
        this.activeUser = activeUser;
        this.useCaseFailed = useCaseFailed;
    }

    public String getActiveUser() {
        return activeUser;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
