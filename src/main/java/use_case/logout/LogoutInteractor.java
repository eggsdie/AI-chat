package use_case.logout;

/**
 * The Logout Interactor.
 */
public class LogoutInteractor implements LogoutInputBoundary {
    private final LogoutUserDataAccessInterface userDataAccessObject;
    private final LogoutOutputBoundary logoutPresenter;

    /**
     * Constructor for the LogoutInteractor.
     *
     * @param userDataAccessObject The data access interface for user data.
     * @param logoutPresenter      The presenter to handle logout output.
     */
    public LogoutInteractor(LogoutUserDataAccessInterface userDataAccessObject,
                            LogoutOutputBoundary logoutPresenter) {
        this.userDataAccessObject = userDataAccessObject;
        this.logoutPresenter = logoutPresenter;
    }

    /**
     * Executes the Logout Use Case.
     *
     * @param logoutInputData Input data containing the username to log out.
     */
    @Override
    public void execute(LogoutInputData logoutInputData) {
        // Get the username from the input data
        String username = logoutInputData.getUsername();

        // Clear the current user session
        userDataAccessObject.setCurrentUsername(null);

        // Prepare the output data with success status
        LogoutOutputData outputData = new LogoutOutputData(username, true);

        // Notify the presenter of the successful logout
        logoutPresenter.prepareSuccessView(outputData);
    }
}
