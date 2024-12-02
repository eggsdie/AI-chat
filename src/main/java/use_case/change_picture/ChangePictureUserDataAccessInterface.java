package use_case.change_picture;

import entity.User;

public interface ChangePictureUserDataAccessInterface {
    void updateUser(User user);
    User get(String username);
}
