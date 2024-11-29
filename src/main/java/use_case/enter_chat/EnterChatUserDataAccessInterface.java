package use_case.enter_chat;

import entity.ChatEntry;
import entity.Friend;
import entity.Message;

import java.util.ArrayList;
import java.util.List;

public interface EnterChatUserDataAccessInterface {

    ArrayList<Message> messagesByChat(String sender, String receiver);

    ArrayList<ChatEntry> allChatsWithUser(String sender);

}
