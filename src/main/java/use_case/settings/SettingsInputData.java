package use_case.settings;

import entity.User;

public class SettingsInputData {
    private final User currentUser;

    public SettingsInputData(User currentUser) {
        this.currentUser = currentUser;
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
