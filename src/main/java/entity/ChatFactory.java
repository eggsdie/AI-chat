package entity;

import java.util.List;

public class ChatFactory {

    public Chat create(List<User> users, String name) {
        return new Chat(users, name);
    }
}
