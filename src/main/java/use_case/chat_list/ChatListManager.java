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
        if (chatListUserDataAccessInterface.friendExists(chatListInputData.getOtherUser())) {
            userPresenter.prepareFailView("Friend already added.");
        }
        else if (chatListUserDataAccessInterface.chatWithYourself(chatListUserDataAccessInterface.getActiveUser(),
                chatListInputData.getOtherUser())) {
            userPresenter.prepareFailView("Can't make a chat with yourself!");
        }
        else {
            final Friend friend = new Friend(chatListInputData.getOtherUser());
            final ChatEntry chat = new ChatEntry(chatListInputData.getCurrentUser(), chatListInputData.getOtherUser(),
                    LocalTime.now(), messagePreview);

            chatListUserDataAccessInterface.saveFriend(friend, chat);

            final ChatListOutputData addChatOutputData = new ChatListOutputData(false, chat);
            userPresenter.prepareSuccessView(addChatOutputData);
        }
    }

    // Remove a chat entry
    public boolean removeChat(String name) {
        return chatListUserDataAccessInterface.getAllChats().removeIf(chat -> chat.getOtherUser().equalsIgnoreCase(name));
    }

    // Retrieve all chats
    public List<ChatEntry> getAllChats() {
        return chatListUserDataAccessInterface.getAllChats();
    }

    // Find a chat by name
    public ChatEntry findChat(String name) {
        for (ChatEntry chat : chatListUserDataAccessInterface.getAllChats()) {
            if (chat.getOtherUser().equalsIgnoreCase(name)) {
                return chat;
            }
        }
        return null;
    }
}

