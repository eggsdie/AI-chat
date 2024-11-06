package use_case.add_chat;

import java.util.List;

import entity.Chat;

public interface AddChatUserDataAccessInterface {

    boolean chatExists(List<String> users);

    void save(Chat chat);
}
