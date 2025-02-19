package interface_adapter.add_friend;

import java.util.ArrayList;

import entity.ChatEntry;
import entity.User;

public class ChatListState {

    private String currentUsername;
    private ArrayList<ChatEntry> chatList;
    private String addFriendError;
    private User activeUser;

    public ChatListState() {
    }

    public String getAddFriendError() {
        return addFriendError;
    }

    public void setAddFriendError(String addFriendError) {
        this.addFriendError = addFriendError;
    }

    public String getCurrentUsername() {
        return currentUsername;
    }

    public void setCurrentUsername(String username) {
        this.currentUsername = username;
    }

    public ArrayList<ChatEntry> getChatList() {
        return chatList;
    }

    public void setChatList(ArrayList<ChatEntry> chatList) {
        this.chatList = chatList;
    }

    public User getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(User activeUser) {
        this.activeUser = activeUser;
    }
}
