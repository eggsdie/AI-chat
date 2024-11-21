package use_case.chat_list;

import entity.ChatEntry;
import entity.Friend;

import java.time.LocalTime;
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
    public void addChat(ChatListInputData chatListInputData, String messagePreview) {
        //if (!chatListUserDataAccessInterface.userExists(chatListInputData.getUser())) {
            //userPresenter.prepareFailView("User does not exist.");
        //}
        if (chatListUserDataAccessInterface.friendExists(chatListInputData.getUser())) {
            userPresenter.prepareFailView("Friend already added.");
        }
        else if (chatListUserDataAccessInterface.chatWithYourself(chatListUserDataAccessInterface.getActiveUser(),
                chatListInputData.getUser())) {
            userPresenter.prepareFailView("Can't make a chat with yourself!");
        }
        else {
            final Friend friend = new Friend(chatListInputData.getUser());
            final ChatEntry chat = new ChatEntry(chatListInputData.getUser(), LocalTime.now(), messagePreview);

            chatListUserDataAccessInterface.saveFriend(friend, chat);

            final ChatListOutputData addChatOutputData = new ChatListOutputData(false);
            userPresenter.prepareSuccessView(addChatOutputData);
        }
    }

    // Remove a chat entry
    public boolean removeChat(String name) {
        return chatListUserDataAccessInterface.getAllChats().removeIf(chat -> chat.getName().equalsIgnoreCase(name));
    }

    // Retrieve all chats
    public List<ChatEntry> getAllChats() {
        return chatListUserDataAccessInterface.getAllChats();
    }

    // Find a chat by name
    public ChatEntry findChat(String name) {
        for (ChatEntry chat : chatListUserDataAccessInterface.getAllChats()) {
            if (chat.getName().equalsIgnoreCase(name)) {
                return chat;
            }
        }
        return null;
    }
}

