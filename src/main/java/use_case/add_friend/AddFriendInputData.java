package use_case.add_friend;

public class AddFriendInputData {
    private final String username;

    public AddFriendInputData(String username) {
        this.username = username;
    }

    public String getUser() {
        return username;
    }

}
