package use_case.add_friend;

import entity.ChatEntry;
import entity.Message;
import entity.User;

import java.util.ArrayList;

public interface AddFriendUserDataAccessInterface {

    boolean existsByName(String username);

    boolean chatWithYourself(String username);

//    void saveFriend(Friend friend, ChatEntry chat);

    ArrayList<Message> messagesWithUser(String username);

    ArrayList<ChatEntry> allChatsWithUser(String username);

    User get(String username);

    void addMessage(String sender, String receiver, String content);

}
