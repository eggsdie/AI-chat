package interface_adapter.settings;

import entity.User;
import interface_adapter.ViewManagerModel;
import use_case.settings.SettingsInputBoundary;
import use_case.settings.SettingsInputData;

public class SettingsController {
    private final SettingsInputBoundary settingsUseCaseInteractor;
    private final ViewManagerModel viewManagerModel;

    public SettingsController(SettingsInputBoundary settingsUseCaseInteractor, ViewManagerModel viewManagerModel) {
        this.settingsUseCaseInteractor = settingsUseCaseInteractor;
        this.viewManagerModel = viewManagerModel;
    }

    public void execute(User currentUser) {
        final SettingsInputData settingsInputData = new SettingsInputData(currentUser);

        settingsUseCaseInteractor.execute(settingsInputData);
    }

    public void switchToSettingsView() {
        viewManagerModel.setState("settings");
    }
}
