package interface_adapter.logout;

import interface_adapter.ViewManagerModel;
import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInputData;

/**
 * The controller for the Logout Use Case.
 */
public class LogoutController {

    private final LogoutInputBoundary logoutUseCaseInteractor;
    private final ViewManagerModel viewManagerModel;

    public LogoutController(LogoutInputBoundary logoutUseCaseInteractor, ViewManagerModel viewManagerModel) {
        this.logoutUseCaseInteractor = logoutUseCaseInteractor;
        this.viewManagerModel = viewManagerModel;
    }

    /**
     * Executes the Logout Use Case and navigates back to the LandingView.
     * @param username the username of the user logging out
     */
    public void execute(String username) {
        final LogoutInputData inputData = new LogoutInputData(username);
        logoutUseCaseInteractor.execute(inputData);

        // Navigate to the LandingView
        viewManagerModel.setState("landing");
    }
}
