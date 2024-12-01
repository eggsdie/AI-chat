package use_case.add_friend;

import java.util.ArrayList;

import entity.ChatEntry;
import entity.Message;
import entity.User;

public interface AddFriendUserDataAccessInterface {

    boolean existsByName(String username);

    boolean chatWithYourself(String username);

    ArrayList<Message> messagesWithUser(String username);

    ArrayList<ChatEntry> allChatsWithUser(String username);

    User get(String username);

    void addMessage(String sender, String receiver, String content);

}
