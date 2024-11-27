package use_case.chat_list;

import entity.ChatEntry;
import entity.Friend;
import entity.Message;
import entity.User;

import java.util.ArrayList;
import java.util.List;

public interface ChatListUserDataAccessInterface {

    boolean existsByName(String username);

    boolean chatWithYourself(String username);

//    void saveFriend(Friend friend, ChatEntry chat);

    ArrayList<Message> messagesWithUser(String username);

    ArrayList<ChatEntry> allChatsWithUser(String username);

    User get(String username);

    void addMessage(String sender, String receiver, String content);

}
