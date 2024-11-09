package use_case.add_chat;

import java.util.List;

import entity.Chat;
import entity.User;

public interface AddChatUserDataAccessInterface {

    boolean chatExists(List<User> users);

    void save(Chat chat);
}
