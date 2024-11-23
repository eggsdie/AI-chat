package use_case.chat_list;

import entity.ChatEntry;

import java.util.List;

public interface ChatListOutputBoundary {
    void presentChatList(List<ChatEntry> chatList);
    void presentError(String errorMessage);

    /**
     * Prepares the success view for the AddChat Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(ChatListOutputData outputData);

    /**
     * Prepares the failure view for the AddChat Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);

    /**
     * Switches to the InChat View.
     */
}

