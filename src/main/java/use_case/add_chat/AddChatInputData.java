package use_case.add_chat;

import java.util.List;

import entity.User;

public class AddChatInputData {
    private List<User> users;
    private String chatName;

    public AddChatInputData(List<User> users, String chatName) {
        this.users = users;
        this.chatName = chatName;
    }

    public List<User> getUsers() {
        return users;
    }

    public String getChatName() {
        return chatName;
    }
}
