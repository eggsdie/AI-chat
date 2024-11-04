package use_case.add_chat;

import entity.User;

import java.util.ArrayList;

public class AddChatInputData {
    private ArrayList<User> users = new ArrayList<User>();

    public AddChatInputData(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<User> getUsers() {
        return users;
    }
}
