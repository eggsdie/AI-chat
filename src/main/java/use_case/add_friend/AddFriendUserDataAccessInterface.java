package use_case.add_friend;

import entity.ChatEntry;
import entity.Friend;

import java.util.List;

public interface AddFriendUserDataAccessInterface {

    boolean userExists(String username);

    boolean friendExists(String username);

    boolean chatWithYourself(String yourUsername, String username);

    void saveFriend(Friend friend, ChatEntry chat);

    String getActiveUser();

    public List<ChatEntry> getAllChats();

}
