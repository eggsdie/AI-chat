package use_case.chat_list;

import entity.ChatEntry;
import entity.Friend;
import entity.User;

import java.util.List;

public interface ChatListUserDataAccessInterface {

    boolean userExists(String username);

    boolean friendExists(String username);

    boolean chatWithYourself(User yourUsername, String username);

    void saveFriend(Friend friend, ChatEntry chat);

    User getActiveUser();

    public List<ChatEntry> getAllChats();

}
