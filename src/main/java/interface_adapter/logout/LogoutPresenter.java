package interface_adapter.logout;

import interface_adapter.ViewManagerModel;
import interface_adapter.settings.SettingsState;
import interface_adapter.settings.SettingsViewModel;
import interface_adapter.signup.SignupViewModel;
import use_case.logout.LogoutOutputBoundary;
import use_case.logout.LogoutOutputData;

/**
 * The Presenter for the Logout Use Case.
 */
public class LogoutPresenter implements LogoutOutputBoundary {

    private final SettingsViewModel settingsViewModel;
    private final ViewManagerModel viewManagerModel;
    private final SignupViewModel signupViewModel;

    public LogoutPresenter(ViewManagerModel viewManagerModel,
                           SettingsViewModel settingsViewModel,
                           SignupViewModel signupViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.settingsViewModel = settingsViewModel;
        this.signupViewModel = signupViewModel;
    }

    @Override
    public void prepareSuccessView(LogoutOutputData response) {
        // Clear the LoggedInState's username
        final SettingsState settingsState = settingsViewModel.getState();
        settingsState.setUsername("");
        settingsViewModel.setState(settingsState);
        settingsViewModel.firePropertyChanged();

        // Switch to the SignupView
        viewManagerModel.setState(signupViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {

    }
}
