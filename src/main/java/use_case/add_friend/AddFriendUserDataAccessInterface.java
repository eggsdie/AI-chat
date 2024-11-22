package use_case.add_friend;

import entity.Chat;
import entity.Friend;

public interface AddFriendUserDataAccessInterface {

    boolean userExists(String username);

    boolean friendExists(String username);

    boolean chatWithYourself(String yourUsername, String username);

    void saveFriend(Friend friend, Chat chat);

    String getActiveUser();

}
