package use_case.login;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import data_access.DemoRestfulApi;

/**
 * The Login Interactor.
 */
public class LoginInteractor implements LoginInputBoundary {
    private final DemoRestfulApi api;
    private final LoginOutputBoundary loginPresenter;

    public LoginInteractor(DemoRestfulApi api, LoginOutputBoundary loginPresenter) {
        this.api = api;
        this.loginPresenter = loginPresenter;
    }

    @Override
    public void execute(LoginInputData loginInputData) {
        final String username = loginInputData.getUsername();
        final String password = loginInputData.getPassword();

        // Fetch all users from the API
        String response = api.getAllUsers();
        try {
            // Parse the API response
            JsonArray usersArray = JsonParser.parseString(response).getAsJsonArray();
            for (int i = 0; i < usersArray.size(); i++) {
                JsonObject user = usersArray.get(i).getAsJsonObject();
                if (user.get("userName").getAsString().equals(username)) {
                    // Validate the password
                    if (user.get("password").getAsString().equals(password)) {
                        // Login successful
                        LoginOutputData outputData = new LoginOutputData(username, false);
                        loginPresenter.prepareSuccessView(outputData);
                        return;
                    } else {
                        // Incorrect password
                        loginPresenter.prepareFailView("Incorrect password for \"" + username + "\".");
                        return;
                    }
                }
            }
            // User not found
            loginPresenter.prepareFailView("Account does not exist.");
        } catch (Exception e) {
            loginPresenter.prepareFailView("An error occurred during login: " + e.getMessage());
        }
    }
}
