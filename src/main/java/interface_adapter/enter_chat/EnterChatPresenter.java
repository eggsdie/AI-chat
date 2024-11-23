package interface_adapter.enter_chat;

import entity.ChatEntry;
import interface_adapter.ViewManagerModel;
import interface_adapter.chat_list.ChatListViewModel;
import use_case.chat_list.ChatListOutputBoundary;
import use_case.chat_list.ChatListOutputData;
import use_case.enter_chat.EnterChatOutputBoundary;
import use_case.enter_chat.EnterChatOutputData;

import java.util.List;

public class EnterChatPresenter implements EnterChatOutputBoundary {

    private final InChatViewModel inChatViewModel;
    private final ViewManagerModel viewManagerModel;
    private final ChatListViewModel chatListViewModel;

    public EnterChatPresenter(ViewManagerModel viewManagerModel,
                              InChatViewModel inChatViewModel, ChatListViewModel chatListViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.inChatViewModel = inChatViewModel;
        this.chatListViewModel = chatListViewModel;
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

    public void switchToChatListView() {
        viewManagerModel.setState(chatListViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

}
