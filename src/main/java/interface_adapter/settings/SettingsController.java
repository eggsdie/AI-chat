package interface_adapter.settings;

import entity.User;
import use_case.settings.SettingsInputBoundary;
import use_case.settings.SettingsInputData;

public class SettingsController {
    protected final SettingsInputBoundary settingsUseCaseInteractor;

    public SettingsController(SettingsInputBoundary settingsUseCaseInteractor) {
        this.settingsUseCaseInteractor = settingsUseCaseInteractor;
    }

    public void execute(User currentUser) {
        final SettingsInputData settingsInputData = new SettingsInputData(currentUser);

        settingsUseCaseInteractor.execute(settingsInputData);
    }

    public void switchToChatListView() {
        settingsUseCaseInteractor.switchToChatListView();
    }
}
