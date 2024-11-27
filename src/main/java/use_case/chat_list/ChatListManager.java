package use_case.chat_list;

import entity.ChatEntry;
import entity.Message;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class ChatListManager implements ChatListInputBoundary {
    private final ChatListUserDataAccessInterface chatListUserDataAccessInterface;
    private final ChatListOutputBoundary userPresenter;

    public ChatListManager(ChatListUserDataAccessInterface chatListUserDataAccessInterface,
                           ChatListOutputBoundary chatListOutputBoundary) {
        this.chatListUserDataAccessInterface = chatListUserDataAccessInterface;
        this.userPresenter = chatListOutputBoundary;
    }

    @Override
    // Add a new chat entry
    public void execute(ChatListInputData chatListInputData, String messagePreview) {
        if (!chatListUserDataAccessInterface.existsByName(chatListInputData.getOtherUser())) {
            userPresenter.prepareFailView("User does not exist.");
        }
        if (friendExists(chatListInputData.getUsername(), chatListInputData.getOtherUser())) {
            userPresenter.prepareFailView("Friend already added.");
        }
        else if (chatListUserDataAccessInterface.chatWithYourself(chatListInputData.getOtherUser())) {
            userPresenter.prepareFailView("Can't make a chat with yourself!");
        }
        else {
            chatListUserDataAccessInterface.addMessage(chatListInputData.getUsername(), chatListInputData.getOtherUser(),
                    messagePreview);
            final ChatListOutputData addChatOutputData = new ChatListOutputData(false,
                    chatListUserDataAccessInterface.allChatsWithUser(chatListInputData.getUsername()),
                    chatListUserDataAccessInterface.get(chatListInputData.getUsername()));
            userPresenter.prepareSuccessView(addChatOutputData);
        }
    }

    // Remove a chat entry
    /*public boolean removeChat(String name) {
        return chatListUserDataAccessInterface.getAllChats().removeIf(chat -> chat.getOtherUser().equalsIgnoreCase(name));
    }*/

    // Retrieve all chats

    public boolean friendExists(String myUsername, String otherUser) {
        for (ChatEntry chatEntry : chatListUserDataAccessInterface.allChatsWithUser(myUsername)) {
            if (chatEntry.getUser1().equals(otherUser) || chatEntry.getUser2().equals(otherUser)) {
                return true;
            }
        }
        return false;
    }

    // Find a chat by name
    /*public ChatEntry findChat(String name) {
        for (ChatEntry chat : getAllChats()) {
            if (chat.getUser2().equalsIgnoreCase(name)) {
                return chat;
            }
        }
        return null;
    }*/
}

