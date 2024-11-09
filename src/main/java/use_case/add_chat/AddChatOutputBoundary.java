package use_case.add_chat;

import use_case.signup.SignupOutputData;

public interface AddChatOutputBoundary {

    /**
     * Prepares the success view for the AddChat Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(AddChatOutputData outputData);

    /**
     * Prepares the failure view for the AddChat Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);

    /**
     * Switches to the InChat View.
     */
    void switchToInChatView();
}
