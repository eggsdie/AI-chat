package use_case.enter_chat;

import java.util.ArrayList;

import entity.ChatEntry;
import entity.Message;

public interface EnterChatUserDataAccessInterface {

    ArrayList<Message> messagesByChat(String sender, String receiver);

    ArrayList<ChatEntry> allChatsWithUser(String sender);

}
