package data_access;

import java.util.ArrayList;
import java.util.List;

import entity.Chat;
import entity.Friend;
import entity.User;
import use_case.add_friend.AddFriendUserDataAccessInterface;

public class InMemoryFriendRepository implements AddFriendUserDataAccessInterface {
    private final List<Friend> friends = new ArrayList<>();
    private final List<Chat> chats = new ArrayList<>();
    private final InMemoryUserDataAccessObject dao;

    public InMemoryFriendRepository(InMemoryUserDataAccessObject dao) {
        this.dao = dao;
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

    public void saveFriend(Friend friend, Chat chat) {
        friends.add(friend);
        chats.add(chat);
    }
}
