package data_access;

import java.util.ArrayList;
import java.util.List;

import entity.ChatEntry;
import entity.Friend;
import entity.User;
import use_case.chat_list.ChatListUserDataAccessInterface;

public class InMemoryFriendRepository implements ChatListUserDataAccessInterface {
    private final List<Friend> friends = new ArrayList<>();
    private final ArrayList<ChatEntry> chats = new ArrayList<>();
    private final InMemoryUserDataAccessObject dao;

    public InMemoryFriendRepository(InMemoryUserDataAccessObject dao) {
        this.dao = dao;
    }

    @Override
    public User getActiveUser() {
        return dao.getCurrentUser();
    }

    public boolean userExists(String username) {
        return dao.existsByName(username);
    }

    @Override
    public boolean friendExists(String username) {
        for (Friend friend : friends) {
            if (friend.getName().equals(username)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean chatWithYourself(User currentUser, String username) {
        return currentUser.getName().equals(username);
    }

    public void saveFriend(Friend friend, ChatEntry chat) {
        friends.add(friend);
        chats.add(chat);
    }

    public List<ChatEntry> getAllChats() {
        return chats;
    }

}
