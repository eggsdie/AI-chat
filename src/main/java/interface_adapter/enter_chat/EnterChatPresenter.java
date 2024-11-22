package interface_adapter.enter_chat;

import entity.ChatEntry;
import interface_adapter.ViewManagerModel;
import use_case.chat_list.ChatListOutputBoundary;
import use_case.chat_list.ChatListOutputData;
import use_case.enter_chat.EnterChatOutputBoundary;
import use_case.enter_chat.EnterChatOutputData;

import java.util.List;

public class EnterChatPresenter implements EnterChatOutputBoundary {

    private final InChatViewModel inChatViewModel;
    private final ViewManagerModel viewManagerModel;

    public EnterChatPresenter(ViewManagerModel viewManagerModel,
                              InChatViewModel inChatViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.inChatViewModel = inChatViewModel;
    }

    @Override
    public void prepareSuccessView(EnterChatOutputData outputData) {
        // On success, switch to the in chat view.
        final InChatState inChatState = inChatViewModel.getState();
        inChatState.setChatEntry(outputData.getChatEntry());
        this.inChatViewModel.setState(inChatState);
        this.inChatViewModel.firePropertyChanged();

        this.viewManagerModel.setState(inChatViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {

    }

}
