package data_access;

import java.util.ArrayList;
import java.util.List;

import entity.Chat;
import use_case.add_chat.AddChatUserDataAccessInterface;

public class InMemoryChatRepository implements AddChatUserDataAccessInterface {
    private final List<Chat> chats = new ArrayList<>();

    public boolean chatExists(List<String> users) {
        return chats.stream().anyMatch(chat -> chat.getUsers().equals(users));
    }

    public void save(Chat chat) {
        chats.add(chat);

    }
}
