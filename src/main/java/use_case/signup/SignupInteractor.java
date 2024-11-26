package use_case.signup;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entity.UserFactory;
import data_access.DemoRestfulApi;

/**
 * The Signup Interactor, responsible for handling the Signup Use Case logic.
 */
public class SignupInteractor implements SignupInputBoundary {
    private final DemoRestfulApi api;
    private final SignupOutputBoundary userPresenter;
    private final UserFactory userFactory;

    /**
     * Constructs a SignupInteractor.
     *
     * @param api                The RESTful API client for making server requests.
     * @param signupOutputBoundary The output boundary for presenting results.
     * @param userFactory         The factory for creating user entities (if needed).
     */
    public SignupInteractor(DemoRestfulApi api,
                            SignupOutputBoundary signupOutputBoundary,
                            UserFactory userFactory) {
        this.api = api;
        this.userPresenter = signupOutputBoundary;
        this.userFactory = userFactory;
    }

    /**
     * Executes the Signup Use Case.
     *
     * @param signupInputData The input data for signing up a user.
     */
    @Override
    public void execute(SignupInputData signupInputData) {
        // Generate placeholder values for additional fields
        String userId = generateUserId();
        String firstName = "DefaultFirstName";
        String lastName = "DefaultLastName";

        // Make the API call to create a new user
        String response = api.createNewUser(
                userId,
                signupInputData.getName(),
                signupInputData.getPassword(),
                signupInputData.getEmail(),
                firstName,
                lastName
        );

        try {
            JsonObject responseJson = JsonParser.parseString(response).getAsJsonObject();

            if (responseJson.has("_id")) {
                // Successful user creation
                SignupOutputData outputData = new SignupOutputData(signupInputData.getName(), false);
                userPresenter.prepareSuccessView(outputData);
                userPresenter.switchToLoginView(); // Automatically switch to the login view
            } else if (responseJson.has("userId") && responseJson.has("userName")) {
                // Duplicate user error
                userPresenter.prepareFailView("Signup failed: User might already exist.");
            } else {
                // Handle other unexpected responses
                userPresenter.prepareFailView("Signup failed: Unexpected server response.");
            }
        } catch (Exception e) {
            userPresenter.prepareFailView("An error occurred during signup: " + e.getMessage());
        }
    }

    /**
     * Switches the user interface to the Login View.
     */
    @Override
    public void switchToLoginView() {
        userPresenter.switchToLoginView();
    }

    /**
     * Generates a unique user ID for the new user.
     *
     * @return A unique user ID based on the current system time.
     */
    private String generateUserId() {
        return "user_" + System.currentTimeMillis();
    }
}
