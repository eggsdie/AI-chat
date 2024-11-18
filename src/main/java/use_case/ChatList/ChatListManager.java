package use_case.ChatList;

import entity.ChatEntry;
import entity.Friend;
import use_case.add_friend.AddFriendOutputBoundary;
import use_case.add_friend.AddFriendOutputData;
import use_case.add_friend.AddFriendUserDataAccessInterface;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ChatListManager implements ChatListInputBoundary {
    private final AddFriendUserDataAccessInterface addFriendUserDataAccessInterface;
    private final ChatListOutputBoundary userPresenter;

    public ChatListManager(AddFriendUserDataAccessInterface addFriendUserDataAccessInterface,
                           ChatListOutputBoundary chatListOutputBoundary) {
        this.addFriendUserDataAccessInterface = addFriendUserDataAccessInterface;
        this.userPresenter = chatListOutputBoundary;
    }

    @Override
    // Add a new chat entry
    public void addChat(ChatListInputData chatListInputData, String messagePreview) {
        if (!addFriendUserDataAccessInterface.userExists(chatListInputData.getUser())) {
            userPresenter.prepareFailView("User does not exist.");
        }
        else if (addFriendUserDataAccessInterface.friendExists(chatListInputData.getUser())) {
            userPresenter.prepareFailView("Friend already added.");
        }
        else if (addFriendUserDataAccessInterface.chatWithYourself(addFriendUserDataAccessInterface.getActiveUser(),
                chatListInputData.getUser())) {
            userPresenter.prepareFailView("Can't make a chat with yourself!");
        }
        else {
            final Friend friend = new Friend(chatListInputData.getUser());
            final ChatEntry chat = new ChatEntry(chatListInputData.getUser(), LocalTime.now(), messagePreview);

            // chatList.add(new ChatEntry(chatListInputData.getUser(), java.time.LocalTime.now(), messagePreview));
            addFriendUserDataAccessInterface.saveFriend(friend, chat);

            final AddFriendOutputData addChatOutputData = new AddFriendOutputData(false);
            userPresenter.prepareSuccessView(addChatOutputData);
        }
    }

    // Remove a chat entry
    public boolean removeChat(String name) {
        return addFriendUserDataAccessInterface.getAllChats().removeIf(chat -> chat.getName().equalsIgnoreCase(name));
    }

    // Retrieve all chats
    public List<ChatEntry> getAllChats() {
        return addFriendUserDataAccessInterface.getAllChats();
    }

    // Find a chat by name
    public ChatEntry findChat(String name) {
        for (ChatEntry chat : addFriendUserDataAccessInterface.getAllChats()) {
            if (chat.getName().equalsIgnoreCase(name)) {
                return chat;
            }
        }
        return null;
    }
}

