package use_case.send_message;

import entity.ChatEntry;

import java.util.List;

public interface SendMessageOutputBoundary {
    void presentChatList(List<ChatEntry> chatList);
    void presentError(String errorMessage);

    /**
     * Prepares the success view for the AddChat Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(SendMessageOutputData outputData);

    /**
     * Prepares the failure view for the AddChat Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);

    /**
     * Switches to the InChat View.
     */
}

