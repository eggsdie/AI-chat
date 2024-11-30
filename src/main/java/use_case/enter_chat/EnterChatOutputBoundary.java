package use_case.enter_chat;

import entity.ChatEntry;

import java.util.ArrayList;
import java.util.List;

public interface EnterChatOutputBoundary {

    /**
     * Prepares the success view for the AddChat Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(EnterChatOutputData outputData);

    /**
     * Prepares the failure view for the AddChat Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);

    /**
     * Switches to the ChatList View.
     * @param chatList chat list for current user
     * */
    void switchToChatListView(ArrayList<ChatEntry> chatList);
}

