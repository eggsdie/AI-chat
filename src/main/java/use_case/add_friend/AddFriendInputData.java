package use_case.add_friend;

import entity.User;

public class AddFriendInputData {
    private final User user;
    private final String otherUser;

    public AddFriendInputData(User user, String otherUser) {
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
